package com.juagosin.coffeetracker.domain.use_case

import com.juagosin.coffeetracker.domain.model.Coffee
import com.juagosin.coffeetracker.domain.repository.CoffeeRepository
import kotlinx.coroutines.flow.Flow

class GetLasCoffeeUseCase( private val repository: CoffeeRepository) {
    operator fun invoke(): Flow<Coffee?> {
        return repository.getLastCoffee()

    }
}