package com.juagosin.coffeetracker.ui.screens.stats

sealed class StatsEvent {
    object ConfirmDelete : StatsEvent()
}