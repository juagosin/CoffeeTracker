package com.calleserpis.coffeetracker.ui.screens.editcoffee

import com.calleserpis.coffeetracker.domain.model.CoffeeType

sealed class EditEvent{
    data class LoadCoffee(val id:Int): EditEvent()
    data class OnPriceChanged(val value: String): EditEvent()
    data class OnDateChanged(val value:Long): EditEvent()
    data class OnCoffeeTypeChanged(val value: CoffeeType): EditEvent()
    object SaveCoffee: EditEvent()
}