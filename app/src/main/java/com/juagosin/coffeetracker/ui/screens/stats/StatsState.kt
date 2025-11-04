package com.juagosin.coffeetracker.ui.screens.stats

import com.juagosin.coffeetracker.domain.model.Coffee

data class StatsState(
    val lastNCoffees: List<Coffee> = emptyList(),
)
