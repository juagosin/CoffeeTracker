package com.juagosin.coffeetracker.ui.screens.stats

import com.juagosin.coffeetracker.domain.model.Coffee
import com.juagosin.coffeetracker.domain.model.stats.AllTimeTypeStats

data class StatsState(
    val lastNCoffees: List<Coffee> = emptyList(),
    val allCoffeesStats: List<AllTimeTypeStats> = emptyList(),
    val coffeeCount: Int = 0,
)
