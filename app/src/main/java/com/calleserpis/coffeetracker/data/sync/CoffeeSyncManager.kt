package com.calleserpis.coffeetracker.data.sync

import android.util.Log
import com.calleserpis.coffeetracker.data.dao.AchievementDao
import com.calleserpis.coffeetracker.data.dao.CoffeeDao
import com.calleserpis.coffeetracker.data.datastore.CoffeePreferencesManager
import com.calleserpis.coffeetracker.data.entity.AchievementEntity
import com.calleserpis.coffeetracker.data.entity.CoffeeEntity
import com.calleserpis.coffeetracker.data.firebase.FirebaseAuthManager
import com.calleserpis.coffeetracker.data.firebase.FirestoreCoffeeRemoteDataSource
import com.calleserpis.coffeetracker.data.mapper.toDomain
import com.calleserpis.coffeetracker.data.mapper.toEntity
import com.calleserpis.coffeetracker.domain.use_case.EvaluateAchievementsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoffeeSyncManager @Inject constructor(
    private val coffeeDao: CoffeeDao,
    private val achievementDao: AchievementDao,
    private val authManager: FirebaseAuthManager,
    private val remoteDataSource: FirestoreCoffeeRemoteDataSource,
    private val preferencesManager: CoffeePreferencesManager,
    private val evaluateAchievementsUseCase: EvaluateAchievementsUseCase
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val syncMutex = Mutex()

    fun initialize() {
        scope.launch {
            runCatching { syncAll() }
                .onFailure { Log.w(TAG, "Sync inicial fallido (se reintentará)", it) }
        }
    }

    fun scheduleSync() {
        scope.launch {
            runCatching { syncAll(reconcileExistingAchievements = false) }
                .onFailure { Log.w(TAG, "Sync fallido", it) }
        }
    }

    suspend fun syncAll(reconcileExistingAchievements: Boolean = true) = syncMutex.withLock {
        val uid = authManager.ensureSignedIn()
        assignRemoteIdsForLegacyRows()
        val coffeesChanged = pullFromRemote(uid)
        if (reconcileExistingAchievements || coffeesChanged) {
            reconcileAchievements()
        }
        syncPendingCoffees(uid)
        syncPendingAchievements(uid)
        preferencesManager.setCloudMigrationCompleted()
    }

    private suspend fun pullFromRemote(uid: String): Boolean {
        val coffeesChanged = pullCoffeesFromRemote(uid)
        pullAchievementsFromRemote(uid)
        return coffeesChanged
    }

    private suspend fun pullCoffeesFromRemote(uid: String): Boolean {
        var coffeesChanged = false
        remoteDataSource.fetchAllCoffees(uid).forEach { remote ->
            val local = coffeeDao.getCoffeeByRemoteId(remote.remoteId)
            if (local == null) {
                coffeeDao.insertCoffee(
                    CoffeeEntity(
                        coffeeType = remote.coffeeType,
                        timestamp = remote.timestamp,
                        notes = remote.notes,
                        price = remote.price,
                        remoteId = remote.remoteId,
                        syncStatus = SyncStatus.SYNCED.name,
                        updatedAt = remote.updatedAt
                    )
                )
                coffeesChanged = true
            } else if (remote.updatedAt > local.updatedAt) {
                coffeeDao.insertCoffee(
                    local.copy(
                        coffeeType = remote.coffeeType,
                        timestamp = remote.timestamp,
                        notes = remote.notes,
                        price = remote.price,
                        updatedAt = remote.updatedAt,
                        syncStatus = SyncStatus.SYNCED.name
                    )
                )
                coffeesChanged = true
            }
        }
        return coffeesChanged
    }

    private suspend fun pullAchievementsFromRemote(uid: String) {
        remoteDataSource.fetchAllAchievements(uid).forEach { remote ->
            val local = achievementDao.getAchievementByIdOrNull(remote.id)
            if (local == null) {
                achievementDao.insert(
                    AchievementEntity(
                        id = remote.id,
                        unlockedAt = remote.unlockedAt,
                        progress = remote.progress,
                        syncStatus = SyncStatus.SYNCED.name,
                        updatedAt = remote.updatedAt
                    )
                )
            } else if (remote.updatedAt > local.updatedAt) {
                achievementDao.insert(
                    local.copy(
                        unlockedAt = remote.unlockedAt,
                        progress = remote.progress,
                        updatedAt = remote.updatedAt,
                        syncStatus = SyncStatus.SYNCED.name
                    )
                )
            }
        }
    }

    private suspend fun assignRemoteIdsForLegacyRows() {
        coffeeDao.getAllCoffeesOnce()
            .filter { it.remoteId.isNullOrBlank() }
            .forEach { entity ->
                val remoteId = UUID.randomUUID().toString()
                coffeeDao.updateSyncMetadata(
                    id = entity.id,
                    remoteId = remoteId,
                    syncStatus = SyncStatus.PENDING_UPLOAD.name,
                    updatedAt = entity.updatedAt.takeIf { it > 0 } ?: entity.timestamp
                )
            }
    }

    private suspend fun reconcileAchievements() {
        val coffees = coffeeDao.getAllCoffeesOnce().toDomain()
        val unlockedIds = achievementDao.getAllAchievementsOnce()
            .mapTo(mutableSetOf()) { it.id }
        val now = System.currentTimeMillis()

        evaluateAchievementsUseCase(
            coffees = coffees,
            unlockedAchievementIds = unlockedIds,
            unlockedAt = now
        ).forEach { achievement ->
            achievementDao.insertIfAbsent(
                achievement.toEntity().copy(
                    syncStatus = SyncStatus.PENDING_UPLOAD.name,
                    updatedAt = now
                )
            )
        }
    }

    private suspend fun syncPendingCoffees(uid: String) {
        coffeeDao.getPendingSyncCoffees().forEach { entity ->
            when (SyncStatus.valueOf(entity.syncStatus)) {
                SyncStatus.PENDING_DELETE -> {
                    entity.remoteId?.let { remoteDataSource.deleteCoffee(uid, it) }
                    coffeeDao.deleteCoffee(entity.id)
                }
                SyncStatus.PENDING_UPLOAD -> {
                    val remoteId = entity.remoteId ?: UUID.randomUUID().toString().also {
                        coffeeDao.updateSyncMetadata(
                            entity.id,
                            it,
                            SyncStatus.PENDING_UPLOAD.name,
                            entity.updatedAt
                        )
                    }
                    remoteDataSource.uploadCoffee(uid, entity, remoteId)
                    coffeeDao.updateSyncMetadata(
                        entity.id,
                        remoteId,
                        SyncStatus.SYNCED.name,
                        System.currentTimeMillis()
                    )
                }
                SyncStatus.SYNCED -> Unit
            }
        }
    }

    private suspend fun syncPendingAchievements(uid: String) {
        achievementDao.getPendingSyncAchievements().forEach { entity ->
            remoteDataSource.uploadAchievement(uid, entity)
            achievementDao.updateSyncMetadata(
                entity.id,
                SyncStatus.SYNCED.name,
                System.currentTimeMillis()
            )
        }
    }

    fun scheduleRemoteDelete(remoteId: String) {
        scope.launch {
            runCatching {
                val uid = authManager.ensureSignedIn()
                remoteDataSource.deleteCoffee(uid, remoteId)
            }.onFailure { Log.w(TAG, "No se pudo borrar café en la nube", it) }
        }
    }

    companion object {
        private const val TAG = "CoffeeSyncManager"
    }
}
