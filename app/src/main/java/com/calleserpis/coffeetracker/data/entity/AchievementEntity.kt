package com.calleserpis.coffeetracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.calleserpis.coffeetracker.data.sync.SyncStatus

@Entity(tableName = "achievements")
data class AchievementEntity(
    @PrimaryKey
    val id: String,
    val unlockedAt: Long?,
    val progress: Int = 0,
    val syncStatus: String = SyncStatus.SYNCED.name,
    val updatedAt: Long = System.currentTimeMillis()
)