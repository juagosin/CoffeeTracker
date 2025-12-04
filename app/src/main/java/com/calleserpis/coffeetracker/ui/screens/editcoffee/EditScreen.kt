package com.calleserpis.coffeetracker.ui.screens.addcoffee

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.calleserpis.coffeetracker.R
import com.calleserpis.coffeetracker.domain.model.CoffeeType
import com.calleserpis.coffeetracker.ui.screens.editcoffee.EditEvent
import com.calleserpis.coffeetracker.ui.screens.editcoffee.EditViewModel
import com.calleserpis.readingAPP.presentation.common.DatePickerTextField

@Composable
fun EditScreen(
    viewModel: EditViewModel = hiltViewModel(),
    onCoffeeSaved: () -> Unit,
    coffeeId: Int
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var selectedOption by remember { mutableStateOf<String?>(null) }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(coffeeId, state.coffee?.type?.name) {
        viewModel.onEvent(EditEvent.LoadCoffee(coffeeId))
        selectedOption = state.coffee?.type?.name
    }

    if (state.isSuccess) {
        onCoffeeSaved()
    }




    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),

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
                    text = stringResource(R.string.title_editCoffee),
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
                            selected = selectedOption == coffee.name,

                            onClick = {
                                selectedOption = coffee.name
                                Log.d(
                                    "TAG",
                                    "EditScreen: ${coffee.name} - ${selectedOption}"
                                )

                                viewModel.onEvent(EditEvent.OnCoffeeTypeChanged(coffee))
                            },
                            label = { Text(stringResource(coffee.displayNameRes)) },
                            shape = RoundedCornerShape(16.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = coffee.color,
                                selectedLabelColor = colorScheme.onPrimary,


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
                        viewModel.onEvent(EditEvent.OnDateChanged(it))
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
                    value = state.priceText ?: "0",
                    onValueChange = { newValue ->
                        if (newValue.isEmpty()) {
                            viewModel.onEvent(EditEvent.OnPriceChanged(""))
                            return@OutlinedTextField
                        }

                        // Permitir solo números, un punto o una coma
                        val filtered = newValue.filter { it.isDigit() || it == '.' || it == ',' }

                        // 2º Validamos que exista solo un separador decimal
                        val decimalSeparators = filtered.count { it == '.' || it == ',' }

                        if (decimalSeparators <= 1) {
                            // Como mucho dejamos dos decimales
                            val parts = filtered.split('.', ',')
                            val isValid = parts.size <= 2 &&
                                    (parts.size == 1 || parts[1].length <= 2)

                            if (isValid) {
                                viewModel.onEvent(EditEvent.OnPriceChanged(filtered))
                            }
                        }
                    },
                    placeholder = {
                        Text(stringResource(R.string.placeholder_price))
                    },
                    label = {

                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = colorScheme.surfaceContainerLowest,
                        unfocusedContainerColor = colorScheme.surfaceContainerLowest,

                        focusedTextColor = colorScheme.onSurface,
                        unfocusedTextColor = colorScheme.onSurface,
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Send
                    ),
                    keyboardActions =
                        KeyboardActions (
                            onSend = {
                                viewModel.onEvent(EditEvent.SaveCoffee)
                                focusManager.clearFocus()
                            }
                        )
                    ,
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {

                        Icon(
                            imageVector = Icons.Default.ShoppingCartCheckout,
                            contentDescription = stringResource(R.string.label_price),
                            tint = colorScheme.primary
                        )

                    }
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    onClick = {
                        viewModel.onEvent(EditEvent.SaveCoffee)
                    },
                    enabled = !state.isSaving
                ) {
                    if (state.isSaving) {
                        CircularProgressIndicator()
                    } else {
                        Text(stringResource(R.string.btn_edit))
                    }
                }
            }
        }
    }

}