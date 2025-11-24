package com.calleserpis.coffeetracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.calleserpis.coffeetracker.ui.screens.addcoffee.AddScreen
import com.calleserpis.coffeetracker.ui.screens.addcoffee.EditScreen
import com.calleserpis.coffeetracker.ui.screens.home.HomeScreen
import com.calleserpis.coffeetracker.ui.screens.stats.StatsScreen

@Composable
fun CoffeeNavHost(modifier: Modifier = Modifier, navController: NavHostController) {
    val EDIT_ROUTE = "edit/{id}"
    NavHost(
        navController = navController,
        startDestination = Screens.Home.route,
        modifier = modifier
    ){
        composable(Screens.Home.route){
            HomeScreen()
        }
        composable(Screens.Stats.route){
            StatsScreen(
                onCoffeeClicked = {

                        coffeeId ->
                    navController.navigate("edit/$coffeeId")
                }
            )
        }
        composable(Screens.Add.route){
            AddScreen(
                onCoffeeSaved = {
                    navController.navigate(Screens.Home.route)
                },
            )
        }
        composable(
            route = EDIT_ROUTE,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ){
            backStackEntry ->
            val coffeeId = backStackEntry.arguments?.getInt("id") ?: 0
            EditScreen(
                onCoffeeSaved = {
                    navController.navigate(Screens.Stats.route)
                },
                coffeeId = coffeeId
            )
        }
    }

}