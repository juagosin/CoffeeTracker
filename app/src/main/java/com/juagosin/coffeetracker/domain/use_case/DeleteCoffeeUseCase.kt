package com.juagosin.coffeetracker.domain.use_case

import com.juagosin.coffeetracker.domain.repository.CoffeeRepository

class DeleteCoffeeUseCase(
    private val coffeeRepository: CoffeeRepository
) {
    suspend operator fun invoke(id: Int?){
        coffeeRepository.deleteCoffee(id)
    }
}