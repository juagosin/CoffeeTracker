package com.calleserpis.coffeetracker.ui.screens.editcoffee

import com.calleserpis.coffeetracker.domain.model.Coffee
import com.calleserpis.coffeetracker.domain.model.CoffeeType

data class EditCoffeeState (
    val id: Int = 0,
    val coffee: Coffee? = null,
    val type: CoffeeType = CoffeeType.entries.first(),
    val date: Long = System.currentTimeMillis(),
    val price: Double = 0.0,
    val priceText: String = "",

    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
)