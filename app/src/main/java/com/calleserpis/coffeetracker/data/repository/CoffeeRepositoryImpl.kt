package com.calleserpis.coffeetracker.data.repository

import com.calleserpis.coffeetracker.data.dao.AchievementDao
import com.calleserpis.coffeetracker.data.dao.CoffeeDao
import com.calleserpis.coffeetracker.data.datastore.CoffeePreferencesManager
import com.calleserpis.coffeetracker.data.firebase.GlobalStatsReporter
import com.calleserpis.coffeetracker.data.mapper.toDomain
import com.calleserpis.coffeetracker.data.mapper.toDomainDailyStats
import com.calleserpis.coffeetracker.data.mapper.toDomainMonthlyStats
import com.calleserpis.coffeetracker.data.mapper.toEntity
import com.calleserpis.coffeetracker.data.mapper.toTypeHistory
import com.calleserpis.coffeetracker.data.sync.CoffeeSyncManager
import com.calleserpis.coffeetracker.data.sync.SyncStatus
import com.calleserpis.coffeetracker.domain.model.Achievement
import com.calleserpis.coffeetracker.domain.model.Coffee
import com.calleserpis.coffeetracker.domain.model.stats.AllTimeTypeStats
import com.calleserpis.coffeetracker.domain.model.stats.DailyCoffeeStat
import com.calleserpis.coffeetracker.domain.model.stats.MonthlyCoffeeStats
import com.calleserpis.coffeetracker.domain.repository.CoffeeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class CoffeeRepositoryImpl @Inject constructor(
    private val coffeeDao: CoffeeDao,
    private val achievementDao: AchievementDao,
    private val preferencesManager: CoffeePreferencesManager,
    private val syncManager: CoffeeSyncManager,
    private val globalStatsReporter: GlobalStatsReporter
) : CoffeeRepository {

    override fun getLastNCoffees(n: Int): Flow<List<Coffee>> {
        return coffeeDao.getLastNCoffees(n).map { it.toDomain() }
    }

    override fun getLastCoffee(): Flow<Coffee?> {
        return coffeeDao.getLastCoffee()
            .map { entity -> entity?.toDomain() }
    }

    override fun getCoffeeById(id: Int): Flow<Coffee?> {
        return coffeeDao.getCoffeeById(id)
            .map { entity -> entity?.toDomain() }
    }

    override suspend fun getCoffeeCount(): Int {
        return coffeeDao.getCoffeeCount()
    }

    override suspend fun getCoffeeCountToday(): Int {
        return coffeeDao.getCoffeeCountToday()
    }

    override suspend fun getSpentMoney(): Double {
        return coffeeDao.getSpentMoney()
    }

    override suspend fun deleteCoffee(id: Int?) {
        if (id == null) return
        val entity = coffeeDao.getCoffeeByIdOnce(id)
        coffeeDao.deleteCoffee(id)
        entity?.remoteId?.let { syncManager.scheduleRemoteDelete(it) }
    }

    override suspend fun insertCoffee(coffee: Coffee) {
        val now = System.currentTimeMillis()
        val isUpdate = coffee.id > 0
        val existing = if (isUpdate) coffeeDao.getCoffeeByIdOnce(coffee.id) else null
        val remoteId = existing?.remoteId ?: UUID.randomUUID().toString()

        coffeeDao.insertCoffee(
            coffee.toEntity().copy(
                remoteId = remoteId,
                syncStatus = SyncStatus.PENDING_UPLOAD.name,
                updatedAt = now
            )
        )
        if (!isUpdate) {
            globalStatsReporter.reportCoffeeAdded(coffee.type.name)
        }
        syncManager.scheduleSync()
    }

    override fun getLastNDaysStats(days: Int): Flow<List<DailyCoffeeStat>> {
        return coffeeDao.getLastNDaysStats(days - 1)
            .map { dayStats -> dayStats.toDomainDailyStats() }
    }

    override fun getAllTimeTypeStats(): Flow<List<AllTimeTypeStats>> {
        return coffeeDao.getAllTimeTypeStats().map {
            historyStats-> historyStats.toTypeHistory()
        }
    }

    override fun getLast12MonthsStats(): Flow<List<MonthlyCoffeeStats>> {
        return coffeeDao.getLast12MonthsStats().map{
            monthStats -> monthStats.toDomainMonthlyStats()
        }
    }

    override fun getLastCoffeePref(): Flow<String?> = preferencesManager.lastCoffeeType

    override suspend fun saveLastCoffeePref(coffeeType: String) {
        preferencesManager.saveLastCoffee(coffeeType)
    }

    override fun getAllUnlockedAchievements(): Flow<List<Achievement>> {
        return achievementDao.getAllUnlocked()
            .map { achievements -> achievements.map { it.toDomain() } }
    }

    override suspend fun getAchievementById(id: String): Achievement {
        TODO("Not yet implemented")
    }

    override suspend fun insertAchievement(achievement: Achievement) {
        achievementDao.insert(
            achievement.toEntity().copy(
                syncStatus = SyncStatus.PENDING_UPLOAD.name,
                updatedAt = System.currentTimeMillis()
            )
        )
        globalStatsReporter.reportAchievementUnlocked(achievement.id)
        syncManager.scheduleSync()
    }

    override suspend fun deleteAchievement(achievementId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllAchievements() {
        TODO("Not yet implemented")
    }
}
