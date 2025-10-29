package com.juagosin.readingAPP.presentation.common

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color

// ui/components/DatePickerTextField.kt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerTextField(
    value: Long?,
    onValueChange: (Long) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Fecha",
    placeholder: String = "Selecciona una fecha",
    enabled: Boolean = true,
    isError: Boolean = false,
    supportingText: String? = null,
    pattern: String = "dd/MM/yyyy HH:mm"
) {
    var showDialog by remember { mutableStateOf(false) }

    val formattedDate = remember(value) {
        value?.let { DateUtils.formatDate(it, pattern) } ?: ""

    }

    OutlinedTextField(
        value = formattedDate,
        onValueChange = { }, // No editable manualmente
        modifier = modifier,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        readOnly = true,
        enabled = enabled,
        isError = isError,
        supportingText = supportingText?.let { { Text(it) } },
        trailingIcon = {
            IconButton(onClick = { if (enabled) showDialog = true }) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = "Seleccionar fecha"
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        interactionSource = remember { MutableInteractionSource() }
            .also { interactionSource ->
                LaunchedEffect(interactionSource) {
                    interactionSource.interactions.collect { interaction ->
                        if (interaction is PressInteraction.Release && enabled) {
                            showDialog = true
                        }
                    }
                }
            }
    )

    if (showDialog) {
        DatePickerDialogCustom(
            initialDate = value,
            onDateSelected = { selectedDate ->
                onValueChange(selectedDate)
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }
}