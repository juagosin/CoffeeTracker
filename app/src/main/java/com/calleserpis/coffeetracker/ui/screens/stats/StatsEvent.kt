package com.calleserpis.coffeetracker.ui.screens.stats

sealed class StatsEvent {
    object ConfirmDelete : StatsEvent()
    data class ToggleCoffeeExpansion(val coffeeId: Int) : StatsEvent()
}