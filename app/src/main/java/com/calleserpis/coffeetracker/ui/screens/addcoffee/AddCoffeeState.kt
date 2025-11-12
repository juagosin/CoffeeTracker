package com.calleserpis.coffeetracker.ui.screens.addcoffee

import com.calleserpis.coffeetracker.domain.model.CoffeeType

data class AddCoffeeState(
    val date: Long = System.currentTimeMillis(),
    val type: CoffeeType = CoffeeType.entries.first(),
    val notes: String = "",
    val price: Double = 0.0,

    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)