package com.juagosin.coffeetracker.data.dao

import androidx.compose.ui.graphics.Color
import com.juagosin.coffeetracker.domain.model.CoffeeType

data class DayStats(
    val day: String,      // Formato: "YYYY-MM-DD"
    val count: Int
)

data class CoffeeTypeCount(
    val coffeeType: CoffeeType,
    val value: Float,
)