package com.juagosin.coffeetracker.ui.navigation


import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.juagosin.coffeetracker.R

@Composable
fun CoffeeBottomBar(){
    NavigationBar {
        NavigationBarItem(
            label = {
                Text(
                    text = stringResource(R.string.screenHome_title)
                )
                    },
            selected = true,
            onClick = { /*TODO*/ },
            icon = {
                Icon( Screens.Home.icon, contentDescription = "")
            },

            )
        NavigationBarItem(
            label = {
                Text(text = stringResource(R.string.screenStats_title))
                    },
            selected = true,
            onClick = { /*TODO*/ },
            icon = {
                Icon( Screens.Stats.icon, contentDescription = "")
            },

            )

    }
}