package com.calleserpis.coffeetracker.domain.use_case

import com.calleserpis.coffeetracker.domain.model.Coffee
import com.calleserpis.coffeetracker.domain.repository.CoffeeRepository

class AddCoffeeUseCase (
    private val repository: CoffeeRepository){

    suspend operator fun invoke(coffee: Coffee) = repository.insertCoffee(coffee)
}