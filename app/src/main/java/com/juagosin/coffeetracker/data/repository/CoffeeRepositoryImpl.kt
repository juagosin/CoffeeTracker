package com.juagosin.coffeetracker.data.repository

import com.juagosin.coffeetracker.data.dao.CoffeeDao
import com.juagosin.coffeetracker.data.mapper.toDomain
import com.juagosin.coffeetracker.data.mapper.toEntity
import com.juagosin.coffeetracker.domain.model.Coffee
import com.juagosin.coffeetracker.domain.repository.CoffeeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CoffeeRepositoryImpl(
    private val coffeeDao: CoffeeDao
) : CoffeeRepository {
    override fun getLastCoffees(): Flow<List<Coffee>> {
        return coffeeDao.getLastCoffees().map { it.toDomain() }

    }

    override suspend fun deleteCoffee(id: Int?) {
        coffeeDao.deleteCoffee(id)
    }

    override suspend fun insertCoffee(coffee: Coffee) {
        coffeeDao.insertCoffee(coffee.toEntity())
    }
}