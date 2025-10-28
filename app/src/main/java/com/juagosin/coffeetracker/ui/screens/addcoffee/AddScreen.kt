package com.juagosin.coffeetracker.ui.screens.addcoffee

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddScreen() {
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp),

        verticalArrangement = Arrangement.Center,
    ){
        Text("Add SCREEN")
    }

}