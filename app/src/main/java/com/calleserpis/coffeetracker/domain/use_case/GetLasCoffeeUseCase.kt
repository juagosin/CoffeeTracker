package com.calleserpis.coffeetracker.domain.use_case

import com.calleserpis.coffeetracker.domain.model.Coffee
import com.calleserpis.coffeetracker.domain.repository.CoffeeRepository
import kotlinx.coroutines.flow.Flow

class GetLasCoffeeUseCase( private val repository: CoffeeRepository) {
    operator fun invoke(): Flow<Coffee?> {
        return repository.getLastCoffee()

    }
}