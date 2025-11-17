package com.calleserpis.coffeetracker.domain.use_case

import com.calleserpis.coffeetracker.domain.repository.CoffeeRepository

class GetCoffeeByIdUseCase(private val repository: CoffeeRepository) {
    operator fun invoke(id: Int) = repository.getCoffeeById(id)
}