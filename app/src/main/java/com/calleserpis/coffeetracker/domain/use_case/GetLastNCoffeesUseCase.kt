package com.calleserpis.coffeetracker.domain.use_case

import com.calleserpis.coffeetracker.domain.model.Coffee
import com.calleserpis.coffeetracker.domain.repository.CoffeeRepository
import kotlinx.coroutines.flow.Flow

class GetLastNCoffeesUseCase(
    private val coffeeRepository: CoffeeRepository
) {
    operator fun invoke(): Flow<List<Coffee>> {
        return coffeeRepository.getLastNCoffees()
    }
}