package com.juagosin.coffeetracker.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

@Serializable
sealed class Screens {
    abstract val route:String
    abstract val icon: ImageVector

    @Serializable
    data object Home: Screens() {
        override val route: String = "home"
        override val icon: ImageVector = Icons.Default.Home
    }
    @Serializable
    data object Stats: Screens() {
        override val route: String = "stats"
        override val icon: ImageVector = Icons.Filled.BarChart
    }
}