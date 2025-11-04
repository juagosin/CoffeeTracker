package com.juagosin.coffeetracker.ui.screens.stats

import android.R.attr.text
import android.widget.ImageButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.juagosin.coffeetracker.R
import com.juagosin.coffeetracker.domain.model.toFormattedDate

@Composable
fun StatsScreen(
    viewModel: StatsViewModel = hiltViewModel()
) {
    val state = viewModel.state
    Column(
        modifier = Modifier.padding(horizontal = 24.dp),

        verticalArrangement = Arrangement.Center
    ) {
        Text( text = "Últimos cafés",
            textAlign = TextAlign.Justify,
            style = MaterialTheme.typography.bodyLarge,)

            ElevatedCard(modifier = Modifier.fillMaxWidth()){
                Column() {
                    state.lastNCoffees.forEach { coffee ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(end = 8.dp),

                            verticalAlignment = Alignment.CenterVertically,

                            ) {
                            Spacer(
                                modifier = Modifier
                                    .width(2.dp)
                                    .height(48.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(2.dp)
                                    )
                            )

                            Text(
                                text = coffee.type.displayName +", el " + coffee.timestamp.toFormattedDate(),
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
}