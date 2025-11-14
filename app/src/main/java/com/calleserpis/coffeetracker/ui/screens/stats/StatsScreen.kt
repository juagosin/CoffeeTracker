package com.calleserpis.coffeetracker.ui.screens.stats

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.calleserpis.coffeetracker.R
import com.calleserpis.coffeetracker.domain.model.Coffee
import com.calleserpis.coffeetracker.domain.model.toFormattedDate
import com.calleserpis.coffeetracker.ui.common.EmptyData
import com.example.app.ui.components.BarChart
import com.example.app.ui.components.BarData

@Composable
fun StatsScreen(
    viewModel: StatsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),

        verticalArrangement = Arrangement.Center
    ) {


        if (state.coffeeCount == 0) {
            EmptyData()

        } else {
            val datosPieChart = remember(state.allCoffeesStats) {
                state.allCoffeesStats.mapIndexed { index, coffee ->
                    PieChartData(
                        label = coffee.coffeeType.displayName,
                        value = coffee.value,
                        color = coffee.coffeeType.color,
                    )
                }
            }
            ModernPieChart(
                titleChart = stringResource(R.string.title_chart_historical),
                data = datosPieChart
            )
            Text(
                text = stringResource(R.string.title_total_spent) + " " + state.moneySpent.toString() + " €",
                textAlign = TextAlign.Right,
                style = MaterialTheme.typography.bodySmall,
                fontStyle = FontStyle.Italic,
                color = colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
            )
            LastCoffees(state, viewModel)
            val datosBarChart = remember(state.last12MonthsStats) {
                state.last12MonthsStats.mapIndexed { index, coffee ->
                    BarData(
                        txtBar = coffee.monthAbbreviation,
                        value = coffee.value.toFloat()
                    )
                }
            }

            BarChart(
                data = datosBarChart,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                title = "Consumo últimos 12 meses ",
                valueLabel = "tazas"
            )
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
                CoffeeListItem(
                    coffee,
                    onDelete = {
                        viewModel.showDeleteDialog(coffee)
                    },
                    isExpanded = state.expandedCoffeeId == coffee.id,
                    onToggleExpansion = {
                        viewModel.onEvent(StatsEvent.ToggleCoffeeExpansion(coffee.id))
                    },
                )
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
fun CoffeeListItem(
    coffee: Coffee,
    onDelete: () -> Unit,
    isExpanded: Boolean,
    onToggleExpansion: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 8.dp)
            .clickable {
                onToggleExpansion()
            },

        verticalAlignment = Alignment.CenterVertically,

        ) {

        Spacer(
            modifier = Modifier
                .width(2.dp)
                .height(48.dp)
                .background(
                    color = coffee.type.color,
                    shape = RoundedCornerShape(2.dp)
                )
        )

        Text(
            text = coffee.type.displayName + ", el " + coffee.timestamp.toFormattedDate(),
            modifier = Modifier.padding(start = 14.dp),
            style = MaterialTheme.typography.bodySmall,
        )
        Spacer(modifier = Modifier.weight(1f))


    }

    AnimatedVisibility(
        visible = isExpanded,
        enter = expandVertically(
            animationSpec = tween(durationMillis = 300)
        ) + fadeIn(
            animationSpec = tween(durationMillis = 300)
        ),
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = 300)
        ) + fadeOut(
            animationSpec = tween(durationMillis = 300)
        )
    ) {
        CoffeeExpanded(
            coffee = coffee,
            onDelete = {
                onDelete()
            }
        )
    }
}

@Composable
fun CoffeeExpanded(
    coffee: Coffee,
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(colorScheme.surfaceContainerLow)
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.label_price) + " " + coffee.price.toString() + " €",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.weight(1f))

           /* IconButton(onClick = {

            }) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = null)
            }
            */

            IconButton(onClick = {
                onDelete()

            }) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
            }
        }

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
