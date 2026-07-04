package com.calleserpis.coffeetracker.data.firebase

import com.calleserpis.coffeetracker.data.entity.AchievementEntity
import com.calleserpis.coffeetracker.data.entity.CoffeeEntity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreCoffeeRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    suspend fun uploadCoffee(uid: String, entity: CoffeeEntity, remoteId: String) {
        val doc = userCoffees(uid).document(remoteId)
        doc.set(
            mapOf(
                FIELD_COFFEE_TYPE to entity.coffeeType,
                FIELD_TIMESTAMP to entity.timestamp,
                FIELD_NOTES to entity.notes,
                FIELD_PRICE to entity.price,
                FIELD_UPDATED_AT to entity.updatedAt
            ),
            SetOptions.merge()
        ).await()
    }

    suspend fun deleteCoffee(uid: String, remoteId: String) {
        userCoffees(uid).document(remoteId).delete().await()
    }

    suspend fun uploadAchievement(uid: String, entity: AchievementEntity) {
        userAchievements(uid).document(entity.id).set(
            mapOf(
                FIELD_UNLOCKED_AT to entity.unlockedAt,
                FIELD_PROGRESS to entity.progress,
                FIELD_UPDATED_AT to entity.updatedAt
            ),
            SetOptions.merge()
        ).await()
    }

    suspend fun fetchAllCoffees(uid: String): List<RemoteCoffee> {
        return userCoffees(uid).get().await().documents.mapNotNull { doc ->
            val coffeeType = doc.getString(FIELD_COFFEE_TYPE) ?: return@mapNotNull null
            val timestamp = doc.getLong(FIELD_TIMESTAMP) ?: return@mapNotNull null
            RemoteCoffee(
                remoteId = doc.id,
                coffeeType = coffeeType,
                timestamp = timestamp,
                notes = doc.getString(FIELD_NOTES),
                price = doc.getDouble(FIELD_PRICE),
                updatedAt = doc.getLong(FIELD_UPDATED_AT) ?: timestamp
            )
        }
    }

    suspend fun fetchAllAchievements(uid: String): List<RemoteAchievement> {
        return userAchievements(uid).get().await().documents.mapNotNull { doc ->
            RemoteAchievement(
                id = doc.id,
                unlockedAt = doc.getLong(FIELD_UNLOCKED_AT),
                progress = doc.getLong(FIELD_PROGRESS)?.toInt() ?: 0,
                updatedAt = doc.getLong(FIELD_UPDATED_AT)
                    ?: doc.getLong(FIELD_UNLOCKED_AT)
                    ?: 0L
            )
        }
    }

    private fun userCoffees(uid: String) =
        firestore.collection(COL_USERS).document(uid).collection(COL_COFFEES)

    private fun userAchievements(uid: String) =
        firestore.collection(COL_USERS).document(uid).collection(COL_ACHIEVEMENTS)

    companion object {
        const val COL_USERS = "users"
        const val COL_COFFEES = "coffees"
        const val COL_ACHIEVEMENTS = "achievements"

        const val FIELD_COFFEE_TYPE = "coffeeType"
        const val FIELD_TIMESTAMP = "timestamp"
        const val FIELD_NOTES = "notes"
        const val FIELD_PRICE = "price"
        const val FIELD_UPDATED_AT = "updatedAt"
        const val FIELD_UNLOCKED_AT = "unlockedAt"
        const val FIELD_PROGRESS = "progress"
    }
}
