package com.juagosin.coffeetracker.ui.screens.stats

import android.R.attr.data
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.juagosin.coffeetracker.R
import com.juagosin.coffeetracker.domain.model.CoffeeType
import com.juagosin.coffeetracker.domain.model.toFormattedDate
import com.juagosin.coffeetracker.ui.common.EmptyData
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

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
        ModernPieChart(titleChart = "Histórico de consumo de café", data = datosPieChart)
        LastCoffees(state)
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun LastCoffees(state: StatsState) {
    Text(
        text = "Últimos cafés",
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
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
                    }

                }
                HorizontalDivider(
                    color = colorScheme.surfaceContainer,
                )
            }

        }
    }
}
