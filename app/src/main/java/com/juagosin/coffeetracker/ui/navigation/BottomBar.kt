package com.juagosin.coffeetracker.ui.navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CoffeBottomBar(){
    NavigationBar {
        NavigationBarItem(
            label = { Text("Home") },
            selected = true,
            onClick = { /*TODO*/ },
            icon = { /*TODO*/ },

            )
    }
}