package com.juagosin.coffeetracker.ui.screens.stats

import android.R.attr.text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.juagosin.coffeetracker.R
import com.juagosin.coffeetracker.domain.model.Coffee
import com.juagosin.coffeetracker.domain.model.CoffeeType
import com.juagosin.coffeetracker.domain.model.toFormattedDate
import com.juagosin.coffeetracker.ui.common.EmptyData

@Composable
fun StatsScreen(
    viewModel: StatsViewModel = hiltViewModel()
) {
    val state = viewModel.state
    Column(
        modifier = Modifier.padding(horizontal = 24.dp).verticalScroll(rememberScrollState()),

        verticalArrangement = Arrangement.Center
    ) {


        if(state.allCoffeesStats.isEmpty()){
            EmptyData()

        }else{
            val datosPieChart = remember(state.allCoffeesStats) {
                state.allCoffeesStats.mapIndexed { index, coffee ->
                    PieChartData(
                        label = coffee.coffeeType.displayName,
                        value = coffee.value,
                        color = coffee.coffeeType.color,
                    )
                }
            }
        ModernPieChart(titleChart = stringResource(R.string.title_chart_historical), data = datosPieChart)
        LastCoffees(state, viewModel)
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun LastCoffees(state: StatsState, viewModel: StatsViewModel) {
    Text(
        text = stringResource(R.string.title_last_coffees),
        textAlign = TextAlign.Justify,
        style = MaterialTheme.typography.bodyLarge,
    )

    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column() {
            state.lastNCoffees.forEach { coffee ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp),

                    verticalAlignment = Alignment.CenterVertically,

                    ) {

                    Spacer(
                        modifier = Modifier
                            .width(2.dp)
                            .height(48.dp)
                            .background(
                                color = if(coffee.type == CoffeeType.DESCAFEINADO) colorScheme.tertiaryContainer else colorScheme.primary,
                                shape = RoundedCornerShape(2.dp)
                            )
                    )

                    Text(
                        text = coffee.type.displayName + ", el " + coffee.timestamp.toFormattedDate(),
                        modifier = Modifier.padding(start = 14.dp),
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {
                        viewModel.showDeleteDialog(coffee)

                    }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
                    }

                }
                HorizontalDivider(
                    color = colorScheme.surfaceContainer,
                )
            }

        }
    }

    state.coffeeToDelete?.let { coffee ->

        DeleteCoffeeDialog(
            coffee = coffee,
            onConfirm = { viewModel.deleteCoffee(coffee.id) },
            onDismiss = { viewModel.dismissDeleteDialog() }
        )
    }
}

@Composable
fun DeleteCoffeeDialog(
    coffee: Coffee,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(R.string.title_delete))
        },
        text = {
            Text(
                text = stringResource(R.string.txt_delete),

            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text(text = stringResource(R.string.btn_delete))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.btn_cancel))
            }
        }
    )
}
