package com.juagosin.coffeetracker.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
                    "Hace 1h 20min",
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .fillMaxSize(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall,
                    color = colorScheme.primary,

                    )
            }
        }


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
    Text("Grafica semanal")
}