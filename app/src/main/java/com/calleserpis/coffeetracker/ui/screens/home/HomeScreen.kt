package com.calleserpis.coffeetracker.ui.screens.home

import TimeDuration
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.calleserpis.coffeetracker.R
import com.calleserpis.coffeetracker.ui.common.EmptyData
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier.padding(horizontal = 24.dp),

        verticalArrangement = Arrangement.Center
    ) {


        if (state.coffeeCount == 0) {

            EmptyData()
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ElevatedCard(
                    modifier = Modifier
                        .height(100.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = colorScheme.surfaceContainerLow,
                        contentColor = colorScheme.onSurfaceVariant
                    ),
                ) {
                    Text(
                        text = stringResource(R.string.title_coffecount),
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
                    modifier = Modifier
                        .height(100.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = colorScheme.surfaceContainerLow,
                        contentColor = colorScheme.onSurfaceVariant
                    ),
                ) {
                    Text(
                        text = stringResource(R.string.title_lastcoffe),
                        textAlign = TextAlign.Center,

                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.bodyLarge,

                        )

                    Text(
                        text = if (state.timeLastCoffee > 0) {
                            TimeDuration.fromTimestamp(state.timeLastCoffee).toSmartString()
                        } else {
                            stringResource(R.string.txt_never)
                        },
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .fillMaxSize(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall,
                        color = colorScheme.primary,

                        )
                }
            }
            CoffeeBarChart(Modifier, state)
        }

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
                text = stringResource(R.string.title_didyouknow),
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
                fontStyle = FontStyle.Italic,
                color = colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
            )
        }
    }

}


@Composable
fun CoffeeBarChart(modifier: Modifier = Modifier, state: HomeState) {


    val maxCount = state.lastNDaysStats.maxOfOrNull { it.count } ?: 1

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
            text = stringResource(R.string.title_lastweek),
            textAlign = TextAlign.Justify,
            style = MaterialTheme.typography.bodyLarge,

            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp, start = 14.dp, end = 14.dp),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(185.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {

            state.lastNDaysStats.forEach { day ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    if (day.count > 0) {
                        Text(
                            text = day.count.toString(),
                            fontSize = 12.sp,
                            color = colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Box(
                            modifier = Modifier
                                .width(24.dp)
                                .height((day.count / maxCount.toFloat()) * 120.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(colorScheme.primary)
                        )
                    }
                    Text(
                        text = day.dayOfWeek,
                        fontSize = 12.sp,
                        color = colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}
