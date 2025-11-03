package com.juagosin.coffeetracker.ui.screens.home

import com.juagosin.coffeetracker.domain.model.stats.DailyCoffeeStat

data class HomeState(
    val randomPhrase: String = "",
    val coffeeCount: Int = 0,
    val timeLastCoffee: Long = 0,
    val lastNDaysStats: List<DailyCoffeeStat> = emptyList()
)