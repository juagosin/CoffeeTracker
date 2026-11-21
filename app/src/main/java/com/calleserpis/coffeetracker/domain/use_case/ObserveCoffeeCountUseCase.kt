package com.calleserpis.coffeetracker.domain.use_case

import com.calleserpis.coffeetracker.domain.repository.CoffeeRepository
import kotlinx.coroutines.flow.Flow

class ObserveCoffeeCountUseCase(
    private val repository: CoffeeRepository
) {
    operator fun invoke(): Flow<Int> = repository.observeCoffeeCount()
}
