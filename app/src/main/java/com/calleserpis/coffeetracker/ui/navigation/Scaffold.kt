package com.calleserpis.coffeetracker.ui.navigation

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
import androidx.navigation.compose.rememberNavController
import com.calleserpis.coffeetracker.R


@Composable
fun CoffeeScaffold(
    modifier: Modifier
) {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CoffeeTopAppBar()
        },
        bottomBar = {
            CoffeeBottomBar(navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screens.Add.route)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.coffeecup),
                    contentDescription = stringResource(R.string.fab_description),
                    modifier = Modifier.size(28.dp)

                )
            }
        }
    ) { innerPadding ->
        CoffeeNavHost(modifier.padding(innerPadding), navController)

    }
}