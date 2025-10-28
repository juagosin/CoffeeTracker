package com.juagosin.coffeetracker.ui.screens.addcoffee

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.juagosin.readingAPP.presentation.common.DatePickerTextField

@Composable
fun AddScreen() {
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp),

        verticalArrangement = Arrangement.Center,
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorScheme.surfaceContainerLow,
                contentColor = colorScheme.onSurfaceVariant
            ),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),

                ) {
                Text(
                    "Nuevo Café ☕",
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(bottom = 8.dp),
                    style = MaterialTheme.typography.headlineSmall,

                    )
                Text(
                    "Tipo",
                    style = MaterialTheme.typography.titleLarge,
                    color = colorScheme.primary
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),

                ) {
                    FilterChip(
                        selected = false,
                        onClick = { // TODO
                        },
                        label = { Text("Solo") },
                        shape = RoundedCornerShape(16.dp),
                    )
                    FilterChip(
                        selected = false,
                        onClick = { // TODO
                        },
                        label = { Text("Cortado") },
                        shape = RoundedCornerShape(16.dp),
                    )
                    FilterChip(
                        selected = false,
                        onClick = { // TODO
                        },
                        label = { Text("Café con leche") },
                        shape = RoundedCornerShape(16.dp),
                    )
                    FilterChip(
                        selected = false,
                        onClick = { // TODO
                        },
                        label = { Text("Carajillo") },
                        shape = RoundedCornerShape(16.dp),
                    )
                    FilterChip(
                        selected = false,
                        onClick = { // TODO
                        },
                        label = { Text("Capuccino") },
                        shape = RoundedCornerShape(16.dp),
                    )


                }

                Text(
                    "Fecha",
                    style = MaterialTheme.typography.titleLarge,
                    color = colorScheme.primary
                )
                DatePickerTextField(
                    value = System.currentTimeMillis(),
                    onValueChange = {},
                    label = "",
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    "Notas",
                    style = MaterialTheme.typography.titleLarge,
                    color = colorScheme.primary
                )
                OutlinedTextField(
                    value = "Notas",
                    onValueChange = {},
                    label = {
                        Text("Notas")
                    },
                    modifier = Modifier.fillMaxWidth()

                )
            }
        }
    }

}