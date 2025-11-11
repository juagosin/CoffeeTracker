package com.calleserpis.coffeetracker.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.calleserpis.coffeetracker.data.dao.CoffeeDao
import com.calleserpis.coffeetracker.data.entity.CoffeeEntity

@Database(entities = [CoffeeEntity::class],
    version = 1)
abstract class CoffeeDatabase: RoomDatabase() {
    abstract val coffeeDao: CoffeeDao
    companion object{
        const val DATABASE_NAME = "coffee_db"
    }
}