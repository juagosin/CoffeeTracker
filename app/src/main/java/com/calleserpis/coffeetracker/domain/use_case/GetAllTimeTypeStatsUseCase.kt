package com.calleserpis.coffeetracker.domain.use_case

import com.calleserpis.coffeetracker.domain.model.stats.AllTimeTypeStats
import com.calleserpis.coffeetracker.domain.repository.CoffeeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTimeTypeStatsUseCase @Inject constructor(
    private val coffeeRepository: CoffeeRepository
) {
    operator  fun invoke(): Flow<List<AllTimeTypeStats>> {
        return coffeeRepository.getAllTimeTypeStats()
    }
}