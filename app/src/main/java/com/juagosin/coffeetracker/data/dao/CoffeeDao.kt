package com.juagosin.coffeetracker.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.juagosin.coffeetracker.data.entity.CoffeeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoffeeDao {
    @Query("SELECT * FROM coffee_table ORDER BY timestamp DESC LIMIT :n")
    fun getLastNCoffees(n: Int=10): Flow<List<CoffeeEntity>>

    @Query("SELECT * FROM coffee_table ORDER BY timestamp DESC LIMIT 1")
    fun getLastCoffee(): Flow<CoffeeEntity>

    @Query("SELECT COUNT(id) FROM coffee_table")
    suspend fun getCoffeeCount(): Int

    @Query("DELETE FROM coffee_table WHERE id = :id")
    suspend fun deleteCoffee(id: Int?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoffee(coffee: CoffeeEntity)

    // Consultas para estadísticas:
    @Query("""
        WITH RECURSIVE days(day, remaining) AS (
            SELECT date('now', '-' || :daysAgo || ' days'), :daysAgo
            UNION ALL
            SELECT date(day, '+1 day'), remaining - 1
            FROM days
            WHERE remaining > 0
        )
        SELECT 
            d.day AS day,
            COALESCE(COUNT(c.id), 0) AS count
        FROM days d
        LEFT JOIN coffee_table c 
            ON date(c.timestamp / 1000, 'unixepoch') = d.day
        GROUP BY d.day
        ORDER BY d.day
    """)
    fun getLastNDaysStats(daysAgo: Int = 6): Flow<List<DayStats>>

    @Query("""
        SELECT 
            coffeeType,
            COUNT(id) AS value
        FROM coffee_table
        GROUP BY coffeeType
        ORDER BY value DESC
    """)
    fun getAllTimeTypeStats(): Flow<List<CoffeeTypeCount>>


}