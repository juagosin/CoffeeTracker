package com.calleserpis.coffeetracker.domain.repository

import com.calleserpis.coffeetracker.domain.model.Coffee
import com.calleserpis.coffeetracker.domain.model.stats.AllTimeTypeStats
import com.calleserpis.coffeetracker.domain.model.stats.DailyCoffeeStat
import com.calleserpis.coffeetracker.domain.model.stats.MonthlyCoffeeStats
import kotlinx.coroutines.flow.Flow

interface CoffeeRepository {

    fun getLastNCoffees(n: Int=10): Flow<List<Coffee>>
    fun getLastCoffee(): Flow<Coffee?>
    fun getCoffeeById(id: Int): Flow<Coffee?>
    suspend fun getCoffeeCount(): Int
    suspend fun getSpentMoney(): Double

    suspend fun deleteCoffee(id: Int?)
    suspend fun insertCoffee(coffee: Coffee)


    // ESTADISTICAS
    fun getLastNDaysStats(days: Int): Flow<List<DailyCoffeeStat>>
    fun getAllTimeTypeStats(): Flow<List<AllTimeTypeStats>>

    fun getLast12MonthsStats(): Flow<List<MonthlyCoffeeStats>>

    //Preferences
    fun getLastCoffeePref(): Flow<String?>
    suspend fun saveLastCoffeePref(coffeeType: String)

}


