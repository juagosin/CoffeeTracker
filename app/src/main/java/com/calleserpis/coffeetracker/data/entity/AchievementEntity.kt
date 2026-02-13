package com.calleserpis.coffeetracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "achievements")
data class AchievementEntity(
    @PrimaryKey
    val id: String,
    val unlockedAt: Long?,
    val progress: Int = 0
)