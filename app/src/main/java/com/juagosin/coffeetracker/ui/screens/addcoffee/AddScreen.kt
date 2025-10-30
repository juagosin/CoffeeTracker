package com.juagosin.coffeetracker.ui.screens.addcoffee

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.juagosin.coffeetracker.R
import com.juagosin.coffeetracker.domain.model.CoffeeType
import com.juagosin.readingAPP.presentation.common.DatePickerTextField

@Composable
fun AddScreen(
    viewModel: AddViewModel = hiltViewModel(),
) {
    val state = viewModel.state
    var selectedOption by remember { mutableStateOf(CoffeeType.entries.first().displayName) }
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
                    text = stringResource(R.string.title_newCoffee),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(bottom = 8.dp),
                    style = MaterialTheme.typography.headlineMedium,

                    )
                Text(
                    text = stringResource(R.string.label_type),
                    style = MaterialTheme.typography.titleLarge,
                    color = colorScheme.primary
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),

                    ) {
                    CoffeeType.entries.forEach { coffee ->
                        FilterChip(
                            selected = selectedOption == coffee.displayName,
                            onClick = {
                                selectedOption = coffee.displayName
                                Log.d("FilterChip", "Selected option: $selectedOption")
                                viewModel.onEvent(AddEvent.OnCoffeeTypeChanged(coffee))
                            },
                            label = { Text(coffee.displayName) },
                            shape = RoundedCornerShape(16.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = colorScheme.tertiaryContainer,
                                selectedLabelColor = colorScheme.onTertiaryContainer,

                                )
                        )
                    }


                }

                Text(
                    text = stringResource(R.string.label_date),
                    style = MaterialTheme.typography.titleLarge,
                    color = colorScheme.primary,
                    modifier = Modifier.padding(top = 8.dp)
                )
                DatePickerTextField(
                    value = state.date,
                    onValueChange = {
                        viewModel.onEvent(AddEvent.OnDateChanged(it))
                    },
                    label = "",
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = stringResource(R.string.label_notes),
                    style = MaterialTheme.typography.titleLarge,
                    color = colorScheme.primary,
                    modifier = Modifier.padding(top = 8.dp)
                )
                OutlinedTextField(
                    value = state.notes,
                    onValueChange = {
                        viewModel.onEvent(AddEvent.OnNotesChanged(it))
                    },
                    label = {

                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    maxLines = 5,
                    modifier = Modifier.fillMaxWidth()

                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    onClick = {
                        viewModel.onEvent(AddEvent.SaveCoffee)
                    },
                    enabled = !state.isSaving
                ) {
                    if(state.isSaving){
                        CircularProgressIndicator()
                    }else {
                        Text(stringResource(R.string.btn_save))
                    }
                }
            }
        }
    }

}