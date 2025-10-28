package com.juagosin.coffeetracker.data

import android.R.attr.version
import androidx.room.Database
import androidx.room.RoomDatabase
import com.juagosin.coffeetracker.data.dao.CoffeeDao
import com.juagosin.coffeetracker.data.entity.CoffeeEntity

@Database(entities = [CoffeeEntity::class],
    version = 1)
abstract class CoffeeDatabase: RoomDatabase() {
    abstract val coffeeDao: CoffeeDao
    companion object{
        const val DATABASE_NAME = "coffee_db"
    }
}