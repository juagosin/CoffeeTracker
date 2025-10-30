package com.juagosin.coffeetracker.ui.screens.addcoffee

import com.juagosin.coffeetracker.domain.model.CoffeeType

sealed class AddEvent {
    data class OnDateChanged(val value:Long): AddEvent()
    data class OnNotesChanged(val value:String): AddEvent()
    data class OnCoffeeTypeChanged(val value: CoffeeType): AddEvent()

    object SaveCoffee: AddEvent()
}