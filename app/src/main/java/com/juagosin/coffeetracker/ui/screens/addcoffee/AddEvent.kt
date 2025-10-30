package com.juagosin.coffeetracker.ui.screens.addcoffee

sealed class AddEvent {
    data class OnDateChanged(val value:Long): AddEvent()
    data class OnNotesChanged(val value:String): AddEvent()
}