package com.calleserpis.coffeetracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.calleserpis.coffeetracker.ui.screens.achievements.AchievementsScreen
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
                    val popped = navController.popBackStack()
                    if (!popped) {
                        navController.navigate(Screens.Home.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
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
                    val popped = navController.popBackStack()
                    if (!popped) {
                        navController.navigate(Screens.Home.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                },
                coffeeId = coffeeId
            )
        }
        composable(Screens.Achievements.route){
            AchievementsScreen()
        }
    }

}