package com.calleserpis.coffeetracker.ui.screens.stats

sealed class StatsEvent {
    object ConfirmDelete : StatsEvent()
}