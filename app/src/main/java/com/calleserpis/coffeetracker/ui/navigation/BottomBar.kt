package com.calleserpis.coffeetracker.ui.navigation


import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.calleserpis.coffeetracker.R

@Composable
fun CoffeeBottomBar(navController: NavHostController){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar {
        NavigationBarItem(
            label = {
                Text(
                    text = stringResource(R.string.screenHome_title)
                )
                    },
            selected = currentRoute == Screens.Home.route,
            onClick = {
                navController.navigate(Screens.Home.route)
            },
            icon = {
                Icon( Screens.Home.icon, contentDescription = "")
            },

            )
        NavigationBarItem(
            label = {
                Text(text = stringResource(R.string.screenStats_title))
                    },
            selected = currentRoute == Screens.Stats.route,
            onClick = {
                navController.navigate(Screens.Stats.route)
            },
            icon = {
                Icon( Screens.Stats.icon, contentDescription = "")
            },

            )

    }
}