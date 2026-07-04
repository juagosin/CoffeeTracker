package com.calleserpis.coffeetracker.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.calleserpis.coffeetracker.data.entity.AchievementEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AchievementDao {
    @Query("SELECT * FROM achievements")
    fun getAllUnlocked(): Flow<List<AchievementEntity>>

    @Query("SELECT * FROM achievements WHERE id = :id")
    suspend fun getAchievementById(id: String): AchievementEntity

    @Query("SELECT * FROM achievements WHERE id = :id LIMIT 1")
    suspend fun getAchievementByIdOrNull(id: String): AchievementEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(achievement: AchievementEntity)

    @Query("DELETE FROM achievements WHERE id = :achievementId")
    suspend fun delete(achievementId: String)

    @Query("DELETE FROM achievements")
    suspend fun deleteAll()

    @Query("SELECT * FROM achievements WHERE syncStatus != 'SYNCED'")
    suspend fun getPendingSyncAchievements(): List<AchievementEntity>

    @Query("SELECT * FROM achievements")
    suspend fun getAllAchievementsOnce(): List<AchievementEntity>

    @Query(
        "UPDATE achievements SET syncStatus = :syncStatus, updatedAt = :updatedAt WHERE id = :id"
    )
    suspend fun updateSyncMetadata(id: String, syncStatus: String, updatedAt: Long)
}