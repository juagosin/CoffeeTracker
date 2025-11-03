package com.juagosin.coffeetracker.domain.repository

import com.juagosin.coffeetracker.domain.model.Coffee
import com.juagosin.coffeetracker.domain.model.stats.DailyCoffeeStat
import kotlinx.coroutines.flow.Flow

interface CoffeeRepository {

    fun getLastCoffees(): Flow<List<Coffee>>
    fun getLastCoffee(): Flow<Coffee?>
    suspend fun getCoffeeCount(): Int

    suspend fun deleteCoffee(id: Int?)
    suspend fun insertCoffee(coffee: Coffee)


    // ESTADISTICAS
    fun getLastNDaysStats(days: Int): Flow<List<DailyCoffeeStat>>
}


