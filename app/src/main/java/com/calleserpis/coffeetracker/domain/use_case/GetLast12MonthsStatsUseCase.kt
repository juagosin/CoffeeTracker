package com.calleserpis.coffeetracker.domain.use_case

import com.calleserpis.coffeetracker.domain.model.stats.MonthlyCoffeeStats
import com.calleserpis.coffeetracker.domain.repository.CoffeeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLast12MonthsStatsUseCase @Inject constructor(
    private val repository: CoffeeRepository
) {
    operator fun invoke(): Flow<List<MonthlyCoffeeStats>> {
        return repository.getLast12MonthsStats()
    }
}