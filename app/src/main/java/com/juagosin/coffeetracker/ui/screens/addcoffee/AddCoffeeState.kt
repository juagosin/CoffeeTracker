package com.juagosin.coffeetracker.ui.screens.addcoffee

import com.juagosin.coffeetracker.domain.model.CoffeeType

data class AddCoffeeState(
    val date: Long = System.currentTimeMillis(),
    val type: CoffeeType = CoffeeType.entries.first(),
    val notes: String? = null,

    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val error: String? = null
)