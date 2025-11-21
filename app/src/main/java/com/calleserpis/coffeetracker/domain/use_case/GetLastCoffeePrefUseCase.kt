package com.calleserpis.coffeetracker.domain.use_case

import com.calleserpis.coffeetracker.domain.repository.CoffeeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLastCoffeePrefUseCase @Inject constructor(
    private val repository: CoffeeRepository
) {
    operator fun invoke(): Flow<String?> = repository.getLastCoffeePref()
}