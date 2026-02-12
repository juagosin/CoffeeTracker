package com.calleserpis.coffeetracker.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.calleserpis.coffeetracker.data.dao.AchievementDao
import com.calleserpis.coffeetracker.data.dao.CoffeeDao
import com.calleserpis.coffeetracker.data.entity.AchievementEntity
import com.calleserpis.coffeetracker.data.entity.CoffeeEntity

@Database(
    entities = [
        CoffeeEntity::class,
        AchievementEntity::class
               ],
    version = 3,
    exportSchema = true // mantiene el histórico de los esquemas
)
abstract class CoffeeDatabase : RoomDatabase() {
    abstract val coffeeDao: CoffeeDao
    abstract val achievementDao: AchievementDao

    companion object {
        const val DATABASE_NAME = "coffee_db"
    }
}