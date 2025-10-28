package com.juagosin.coffeetracker.domain.repository

import com.juagosin.coffeetracker.domain.model.Coffee
import kotlinx.coroutines.flow.Flow

interface CoffeeRepository {

    fun getLastCoffees(): Flow<List<Coffee>>

    suspend fun deleteCoffee(id: Int?)
    suspend fun insertCoffee(coffee: Coffee)
}


