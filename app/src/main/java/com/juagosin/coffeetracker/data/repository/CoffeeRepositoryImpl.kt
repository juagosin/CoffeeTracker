package com.juagosin.coffeetracker.data.repository

import com.juagosin.coffeetracker.data.dao.CoffeeDao
import com.juagosin.coffeetracker.data.mapper.toDomain
import com.juagosin.coffeetracker.data.mapper.toDomainDailyStats
import com.juagosin.coffeetracker.data.mapper.toEntity
import com.juagosin.coffeetracker.domain.model.Coffee
import com.juagosin.coffeetracker.domain.model.stats.DailyCoffeeStat
import com.juagosin.coffeetracker.domain.repository.CoffeeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CoffeeRepositoryImpl(
    private val coffeeDao: CoffeeDao
) : CoffeeRepository {

    override fun getLastNCoffees(n: Int): Flow<List<Coffee>> {
        return coffeeDao.getLastNCoffees().map { it.toDomain() }
    }

    override fun getLastCoffee(): Flow<Coffee?> {
        return coffeeDao.getLastCoffee()
            .map { entity -> entity?.toDomain() }
    }

    override suspend fun getCoffeeCount(): Int {
        return coffeeDao.getCoffeeCount()
    }

    override suspend fun deleteCoffee(id: Int?) {
        coffeeDao.deleteCoffee(id)
    }

    override suspend fun insertCoffee(coffee: Coffee) {
        coffeeDao.insertCoffee(coffee.toEntity())
    }

    override fun getLastNDaysStats(days: Int): Flow<List<DailyCoffeeStat>> {
        return coffeeDao.getLastNDaysStats(days - 1)
            .map { dayStats -> dayStats.toDomainDailyStats() }
    }
}