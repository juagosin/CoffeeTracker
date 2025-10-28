package com.juagosin.coffeetracker.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.juagosin.coffeetracker.Greeting


@Composable
fun CoffeeScaffold( modifier: Modifier
){

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            CoffeTopAppBar()
        },
        bottomBar = {
            CoffeBottomBar()
        }
    ) { innerPadding ->
        Greeting(
            name = "Android",
            modifier = Modifier.padding(innerPadding)
        )
    }
}