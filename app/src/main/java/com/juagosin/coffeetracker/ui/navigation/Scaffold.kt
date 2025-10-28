package com.juagosin.coffeetracker.ui.navigation

import android.R.attr.resource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.juagosin.coffeetracker.Greeting
import com.juagosin.coffeetracker.R


@Composable
fun CoffeeScaffold( modifier: Modifier
){

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            CoffeTopAppBar()
        },
        bottomBar = {
            CoffeBottomBar()
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO FAB logic */ }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.coffeecup),
                    contentDescription = stringResource(R.string.fab_description),
                    modifier = Modifier.size(28.dp)

                )
            }
        }
    ) { innerPadding ->
        Greeting(
            name = "Android",
            modifier = Modifier.padding(innerPadding)
        )
    }
}