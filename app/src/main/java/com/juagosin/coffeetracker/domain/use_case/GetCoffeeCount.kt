package com.juagosin.coffeetracker.domain.use_case

import com.juagosin.coffeetracker.domain.repository.CoffeeRepository

class GetCoffeeCount( private val repository: CoffeeRepository) {
    suspend operator fun invoke():Int{
        return repository.getCoffeeCount()
    }
}