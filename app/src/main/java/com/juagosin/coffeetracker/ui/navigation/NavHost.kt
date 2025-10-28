package com.juagosin.coffeetracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.juagosin.coffeetracker.ui.screens.home.HomeScreen
import com.juagosin.coffeetracker.ui.screens.stats.StatsScreen

@Composable
fun CoffeeNavHost(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.Home.route,
        modifier = modifier
    ){
        composable(Screens.Home.route){
            HomeScreen()
        }
        composable(Screens.Stats.route){
            StatsScreen()
        }
    }

}