package com.juagosin.coffeetracker.domain.use_case

data class CoffeeUseCases(
    val addCoffeeUseCase: AddCoffeeUseCase,
    val getCoffeeCount: GetCoffeeCount,
    val getLasCoffeeUseCase: GetLasCoffeeUseCase,
    val getLastNDaysCoffeeUseCase: GetLastNDaysStatsUseCase
)
