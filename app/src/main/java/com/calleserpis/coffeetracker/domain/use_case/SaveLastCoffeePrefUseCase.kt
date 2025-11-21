package com.calleserpis.coffeetracker.domain.use_case

import com.calleserpis.coffeetracker.domain.repository.CoffeeRepository
import javax.inject.Inject

class SaveLastCoffeePrefUseCase @Inject constructor(
    private val repository: CoffeeRepository
) {
    suspend operator fun invoke(coffeeType: String) {
        repository.saveLastCoffeePref(coffeeType)
    }
}