package com.juagosin.coffeetracker.domain.repository

import com.juagosin.coffeetracker.domain.model.Coffee
import com.juagosin.coffeetracker.domain.model.stats.DailyCoffeeStat
import com.juagosin.coffeetracker.domain.model.stats.AllTimeTypeStats
import com.juagosin.coffeetracker.domain.model.stats.MonthlyCoffeeStats
import kotlinx.coroutines.flow.Flow

interface CoffeeRepository {

    fun getLastNCoffees(n: Int=10): Flow<List<Coffee>>
    fun getLastCoffee(): Flow<Coffee?>
    suspend fun getCoffeeCount(): Int

    suspend fun deleteCoffee(id: Int?)
    suspend fun insertCoffee(coffee: Coffee)


    // ESTADISTICAS
    fun getLastNDaysStats(days: Int): Flow<List<DailyCoffeeStat>>
    fun getAllTimeTypeStats(): Flow<List<AllTimeTypeStats>>

    fun getLast12MonthsStats(): Flow<List<MonthlyCoffeeStats>>

}


