package com.calleserpis.coffeetracker.domain.use_case

import com.calleserpis.coffeetracker.domain.repository.CoffeeRepository

class GetCoffeeCount( private val repository: CoffeeRepository) {
    suspend operator fun invoke():Int{
        return repository.getCoffeeCount()
    }
}