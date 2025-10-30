package com.juagosin.coffeetracker.domain.use_case

import com.juagosin.coffeetracker.domain.model.Coffee
import com.juagosin.coffeetracker.domain.repository.CoffeeRepository

class AddCoffeeUseCase (
    private val repository: CoffeeRepository){

    suspend operator fun invoke(coffee: Coffee) = repository.insertCoffee(coffee)
}