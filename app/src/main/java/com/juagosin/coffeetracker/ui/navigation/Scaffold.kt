package com.juagosin.coffeetracker.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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