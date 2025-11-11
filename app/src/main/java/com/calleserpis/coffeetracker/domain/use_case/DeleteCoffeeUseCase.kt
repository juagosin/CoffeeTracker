package com.calleserpis.coffeetracker.domain.use_case

import com.calleserpis.coffeetracker.domain.repository.CoffeeRepository

class DeleteCoffeeUseCase(
    private val coffeeRepository: CoffeeRepository
) {
    suspend operator fun invoke(id: Int?){
        coffeeRepository.deleteCoffee(id)
    }
}