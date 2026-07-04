package com.calleserpis.coffeetracker.data.firebase

data class RemoteCoffee(
    val remoteId: String,
    val coffeeType: String,
    val timestamp: Long,
    val notes: String?,
    val price: Double?,
    val updatedAt: Long
)

data class RemoteAchievement(
    val id: String,
    val unlockedAt: Long?,
    val progress: Int,
    val updatedAt: Long
)
