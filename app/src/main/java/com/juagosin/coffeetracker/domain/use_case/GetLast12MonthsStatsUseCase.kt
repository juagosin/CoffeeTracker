package com.juagosin.coffeetracker.domain.use_case

import com.juagosin.coffeetracker.data.dao.MonthStats
import com.juagosin.coffeetracker.domain.model.stats.MonthlyCoffeeStats
import com.juagosin.coffeetracker.domain.repository.CoffeeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLast12MonthsStatsUseCase @Inject constructor(
    private val repository: CoffeeRepository
) {
    operator fun invoke(): Flow<List<MonthlyCoffeeStats>> {
        return repository.getLast12MonthsStats()
    }
}