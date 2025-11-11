package com.calleserpis.coffeetracker.data.repository

import com.calleserpis.coffeetracker.data.dao.CoffeeDao

import com.calleserpis.coffeetracker.data.mapper.toDomain
import com.calleserpis.coffeetracker.data.mapper.toDomainDailyStats
import com.calleserpis.coffeetracker.data.mapper.toDomainMonthlyStats
import com.calleserpis.coffeetracker.data.mapper.toEntity
import com.calleserpis.coffeetracker.data.mapper.toTypeHistory
import com.calleserpis.coffeetracker.domain.model.Coffee
import com.calleserpis.coffeetracker.domain.model.stats.DailyCoffeeStat
import com.calleserpis.coffeetracker.domain.model.stats.AllTimeTypeStats
import com.calleserpis.coffeetracker.domain.model.stats.MonthlyCoffeeStats
import com.calleserpis.coffeetracker.domain.repository.CoffeeRepository
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

    override fun getAllTimeTypeStats(): Flow<List<AllTimeTypeStats>> {
        return coffeeDao.getAllTimeTypeStats().map {
            historyStats-> historyStats.toTypeHistory()
        }
    }

    override fun getLast12MonthsStats(): Flow<List<MonthlyCoffeeStats>> {
        return coffeeDao.getLast12MonthsStats().map{
            monthStats -> monthStats.toDomainMonthlyStats()
        }
    }


}