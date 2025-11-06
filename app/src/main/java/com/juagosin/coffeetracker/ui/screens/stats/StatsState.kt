package com.juagosin.coffeetracker.ui.screens.stats

import com.juagosin.coffeetracker.domain.model.Coffee
import com.juagosin.coffeetracker.domain.model.stats.AllTimeTypeStats
import com.juagosin.coffeetracker.domain.model.stats.MonthlyCoffeeStats

data class StatsState(
    val lastNCoffees: List<Coffee> = emptyList(),
    val allCoffeesStats: List<AllTimeTypeStats> = emptyList(),
    val last12MonthsStats: List<MonthlyCoffeeStats> = emptyList(),
    val coffeeCount: Int = 0,
    val confirmDialog: Boolean = false,
    val coffeeToDelete: Coffee? = null,
)
