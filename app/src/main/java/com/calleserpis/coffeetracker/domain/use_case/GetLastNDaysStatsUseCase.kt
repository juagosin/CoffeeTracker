package com.calleserpis.coffeetracker.domain.use_case

import com.calleserpis.coffeetracker.domain.model.stats.DailyCoffeeStat
import com.calleserpis.coffeetracker.domain.repository.CoffeeRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetLastNDaysStatsUseCase @Inject constructor(
    private val repository: CoffeeRepository
) {
    operator fun invoke(days: Int): Flow<List<DailyCoffeeStat>> {
        return repository.getLastNDaysStats(days)
    }
}