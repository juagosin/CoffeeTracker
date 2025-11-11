package com.calleserpis.coffeetracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coffee_table")
data class CoffeeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val coffeeType: String,
    val timestamp: Long = System.currentTimeMillis(),
    val notes: String? = null
)
