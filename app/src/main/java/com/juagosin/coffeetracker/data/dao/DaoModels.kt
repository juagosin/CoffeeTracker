package com.juagosin.coffeetracker.data.dao

import com.juagosin.coffeetracker.domain.model.CoffeeType

data class DayStats(
    val day: String,
    val count: Int
)

data class CoffeeTypeCount(
    val coffeeType: CoffeeType,
    val value: Float,
)