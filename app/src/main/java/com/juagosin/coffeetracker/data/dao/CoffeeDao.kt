package com.juagosin.coffeetracker.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.juagosin.coffeetracker.data.entity.CoffeeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoffeeDao {
    @Query("SELECT * FROM coffee_table ORDER BY timestamp DESC LIMIT 10")
    fun getLastCoffees(): Flow<List<CoffeeEntity>>

    @Query("SELECT COUNT(id) FROM coffee_table")
    suspend fun getCoffeeCount(): Int

    @Query("DELETE FROM coffee_table WHERE id = :id")
    suspend fun deleteCoffee(id: Int?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoffee(coffee: CoffeeEntity)
}