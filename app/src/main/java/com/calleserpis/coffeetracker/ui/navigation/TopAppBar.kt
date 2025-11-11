package com.calleserpis.coffeetracker.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.calleserpis.coffeetracker.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeeTopAppBar(){
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.Main_title) + "☕",
                modifier = Modifier.padding(12.dp),

            )
        },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}