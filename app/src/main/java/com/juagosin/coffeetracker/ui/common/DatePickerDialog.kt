package com.juagosin.readingAPP.presentation.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.juagosin.coffeetracker.R
import java.util.Calendar


// ui/components/DatePickerDialog.kt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogCustom(
    initialDate: Long?,
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    var showTimePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDate ?: System.currentTimeMillis()
    )

    if (!showTimePicker) {
        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(
                    onClick = {
                        if (datePickerState.selectedDateMillis != null) {
                            showTimePicker = true
                        }
                    }
                ) {
                    Text(text = stringResource(R.string.btn_accept))
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(text = stringResource(R.string.btn_cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState, showModeToggle = true)
        }
    } else {
        // --- TIME PICKER OFICIAL DE MATERIAL 3 ---
        val calendar = Calendar.getInstance().apply {
            timeInMillis = datePickerState.selectedDateMillis ?: System.currentTimeMillis()
        }

        val timePickerState = rememberTimePickerState(
            initialHour = calendar.get(Calendar.HOUR_OF_DAY),
            initialMinute = calendar.get(Calendar.MINUTE),
            is24Hour = true
        )

        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    val combinedMillis = Calendar.getInstance().apply {
                        timeInMillis = datePickerState.selectedDateMillis ?: System.currentTimeMillis()
                        set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                        set(Calendar.MINUTE, timePickerState.minute)
                    }.timeInMillis

                    onDateSelected(combinedMillis)
                    showTimePicker = false
                }) {
                    Text(text = stringResource(R.string.btn_accept))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showTimePicker = false
                    onDismiss()
                }) {
                    Text(text = stringResource(R.string.btn_cancel))
                }
            },
            title = { Text("Selecciona hora") },
            text = {
                TimePicker(state = timePickerState)
            }
        )
    }
}