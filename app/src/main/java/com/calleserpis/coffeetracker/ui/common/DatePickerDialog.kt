package com.calleserpis.readingAPP.presentation.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
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
import com.calleserpis.coffeetracker.R
import java.util.Calendar
import java.util.TimeZone



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogCustom(
    initialDate: Long?,
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    var showTimePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDate ?: System.currentTimeMillis(),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                // Obtener la fecha de hoy a las 23:59:59
                val todayUtc = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
                    timeInMillis = System.currentTimeMillis()
                    set(Calendar.HOUR_OF_DAY, 23)
                    set(Calendar.MINUTE, 59)
                    set(Calendar.SECOND, 59)
                    set(Calendar.MILLISECOND, 999)
                }.timeInMillis

                return utcTimeMillis <= todayUtc
            }
        }
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
        // Convertir de UTC (DatePicker) a zona horaria local
        val selectedDateUtc = datePickerState.selectedDateMillis ?: System.currentTimeMillis()

        // Obtener la fecha seleccionada en la zona horaria local
        val localCalendar = Calendar.getInstance().apply {
            timeInMillis = selectedDateUtc
            // El DatePicker devuelve medianoche UTC, ajustamos a la zona local
            set(Calendar.HOUR_OF_DAY, get(Calendar.HOUR_OF_DAY))
            set(Calendar.MINUTE, get(Calendar.MINUTE))
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val timePickerState = rememberTimePickerState(
            initialHour = localCalendar.get(Calendar.HOUR_OF_DAY),
            initialMinute = localCalendar.get(Calendar.MINUTE),
            is24Hour = true
        )

        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    val finalCalendar = Calendar.getInstance().apply {
                        timeInMillis = selectedDateUtc
                        set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                        set(Calendar.MINUTE, timePickerState.minute)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)
                    }

                    onDateSelected(finalCalendar.timeInMillis)
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