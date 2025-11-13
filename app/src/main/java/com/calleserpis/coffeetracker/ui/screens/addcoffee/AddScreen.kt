package com.calleserpis.coffeetracker.ui.screens.addcoffee

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.calleserpis.coffeetracker.R
import com.calleserpis.coffeetracker.domain.model.CoffeeType
import com.calleserpis.readingAPP.presentation.common.DatePickerTextField

@Composable
fun AddScreen(
    viewModel: AddViewModel = hiltViewModel(),
    onCoffeeSaved: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    if (state.isSuccess) {
        onCoffeeSaved()
    }
    var selectedOption by remember { mutableStateOf(CoffeeType.entries.first().displayName) }
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp).verticalScroll(rememberScrollState()),

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
                    modifier = Modifier.fillMaxWidth(),

                )
                Text(
                    text = stringResource(R.string.label_price),
                    style = MaterialTheme.typography.titleLarge,
                    color = colorScheme.primary,
                    modifier = Modifier.padding(top = 8.dp)
                )
                OutlinedTextField(
                    value = state.price.toString(),
                    onValueChange = { newValue ->
                        // Permitir solo números, un punto o una coma
                        val filtered = newValue.filter { it.isDigit() || it == '.' || it == ',' }

                        // Validar formato decimal correcto
                        val decimalSeparators = filtered.count { it == '.' || it == ',' }

                        if (decimalSeparators <= 1) {
                            // Opcional: limitar decimales a 2 dígitos
                            val parts = filtered.split('.', ',')
                            val isValid = parts.size <= 2 &&
                                    (parts.size == 1 || parts[1].length <= 2)

                            if (isValid) {
                                viewModel.onEvent(AddEvent.OnPriceChanged(filtered))
                            }
                        }
                    },
                    label = {

                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = colorScheme.surfaceContainerLowest,
                        unfocusedContainerColor = colorScheme.surfaceContainerLowest,

                        focusedTextColor = colorScheme.onSurface,
                        unfocusedTextColor = colorScheme.onSurface,
                    ),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Number),
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