package com.juagosin.coffeetracker.ui.screens.home

import android.R.attr.top
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state
    Column(
        modifier = Modifier.padding(horizontal = 24.dp),

        verticalArrangement = Arrangement.Center
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ElevatedCard(
                modifier = Modifier.height(100.dp).weight(1f),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorScheme.surfaceContainerLow,
                    contentColor = colorScheme.onSurfaceVariant
                ),
            ) {
                Text(
                    "Contador de cafés",
                    textAlign = TextAlign.Center,

                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge,
                )

                Text(
                    text = state.coffeeCount.toString(),
                    textAlign = TextAlign.Center,
                    color = colorScheme.primary,

                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.titleLarge,

                    )
            }

            ElevatedCard(
                modifier = Modifier.height(100.dp).weight(1f),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorScheme.surfaceContainerLow,
                    contentColor = colorScheme.onSurfaceVariant
                ),
            ) {
                Text(
                    "Último café",
                    textAlign = TextAlign.Center,

                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge,

                    )

                Text(
                    text = if (state.timeLastCoffee>0){ TimeDuration.fromTimestamp(state.timeLastCoffee).toSmartString()} else{ "Nunca"},
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .fillMaxSize(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall,
                    color = colorScheme.primary,

                    )
            }
        }

        CoffeeBarChart()
        WeekStats()

        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorScheme.surfaceContainerLow,
                contentColor = colorScheme.onSurfaceVariant
            ),
        ) {
            Text(
                text = "Sabías que...",
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.titleLarge,
                color = colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 14.dp, start = 14.dp, end = 14.dp),
            )
            Text(
                state.randomPhrase,
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.bodySmall,
                color = colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
            )
        }
    }

}


@Composable
fun WeekStats(

) {
   // Text("Grafica semanal")
}

@Composable
fun CoffeeBarChart(modifier: Modifier = Modifier) {
    val coffeeData = listOf(
        CoffeeDay("Lun", 3),
        CoffeeDay("Mar", 2),
        CoffeeDay("Mié", 7),
        CoffeeDay("Jue", 1),
        CoffeeDay("Vie", 5),
        CoffeeDay("Sáb", 2),
        CoffeeDay("Dom", 3)
    )

    val maxCount = coffeeData.maxOf { it.count }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surfaceContainerLow,
            contentColor = colorScheme.onSurfaceVariant
        ),
    ) {
        Text(
            text = "Cafés de la última semana",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.surfaceContainerLow,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(175.dp), // altura total de la gráfica
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom // 👈 CLAVE: las barras se anclan abajo
        ){
            coffeeData.forEach { day ->
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom) {
                    Text(
                        text = day.count.toString(),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Box(
                        modifier = Modifier
                            .width(24.dp)
                            .height((day.count / maxCount.toFloat()) * 120.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.primary)
                    )
                    Text(
                        text = day.day,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

data class CoffeeDay(val day: String, val count: Int)