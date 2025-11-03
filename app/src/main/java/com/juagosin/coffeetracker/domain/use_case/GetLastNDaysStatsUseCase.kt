package com.juagosin.coffeetracker.domain.use_case

import com.juagosin.coffeetracker.domain.model.stats.DailyCoffeeStat
import com.juagosin.coffeetracker.domain.repository.CoffeeRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetLastNDaysStatsUseCase @Inject constructor(
    private val repository: CoffeeRepository
) {
    operator fun invoke(days: Int): Flow<List<DailyCoffeeStat>> {
        return repository.getLastNDaysStats(days)
    }
}