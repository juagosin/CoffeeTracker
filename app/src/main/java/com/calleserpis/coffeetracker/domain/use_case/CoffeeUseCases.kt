package com.calleserpis.coffeetracker.domain.use_case

data class CoffeeUseCases(
    val addCoffeeUseCase: AddCoffeeUseCase,
    val getCoffeeCount: GetCoffeeCount,
    val getMoneySpent: GetMoneySpent,
    val getLasCoffeeUseCase: GetLasCoffeeUseCase,
    val getLastNDaysCoffeeUseCase: GetLastNDaysStatsUseCase,
    val getLastNCoffeesUseCase: GetLastNCoffeesUseCase,
    val getAllTimeTypeStatsUseCase: GetAllTimeTypeStatsUseCase,
    val deleteCoffeeUseCase: DeleteCoffeeUseCase,
    val getLast12MonthsStatsUseCase: GetLast12MonthsStatsUseCase
)
