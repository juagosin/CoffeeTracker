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

    // Convertir timestamp local a UTC para el DatePicker
    val initialDateUtc = remember(initialDate) {
        if (initialDate != null) {
            val localCal = Calendar.getInstance().apply {
                timeInMillis = initialDate
            }
            // Obtener los componentes de fecha en zona local
            val year = localCal.get(Calendar.YEAR)
            val month = localCal.get(Calendar.MONTH)
            val day = localCal.get(Calendar.DAY_OF_MONTH)

            // Crear la misma fecha en UTC y devolver el timeInMillis
            Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
                set(year, month, day, 0, 0, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis
        } else {
            // Fecha actual en UTC medianoche
            val now = Calendar.getInstance()
            Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
                set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH), 0, 0, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis
        }
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDateUtc,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                // Obtener medianoche de hoy en UTC
                val now = Calendar.getInstance()
                val todayUtc = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
                    set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH), 23, 59, 59)
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
        // CRÍTICO: Convertir UTC del DatePicker a componentes de fecha en zona local
        val selectedDateUtc = datePickerState.selectedDateMillis ?: System.currentTimeMillis()

        // Extraer año, mes, día de la fecha UTC seleccionada
        val utcCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
            timeInMillis = selectedDateUtc
        }
        val year = utcCalendar.get(Calendar.YEAR)
        val month = utcCalendar.get(Calendar.MONTH)
        val day = utcCalendar.get(Calendar.DAY_OF_MONTH)

        // Obtener hora actual o de initialDate si existe
        val currentTime = Calendar.getInstance().apply {
            if (initialDate != null) {
                timeInMillis = initialDate
            }
        }

        val timePickerState = rememberTimePickerState(
            initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
            initialMinute = currentTime.get(Calendar.MINUTE),
            is24Hour = true
        )

        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    // Crear fecha en zona local con los componentes extraídos
                    val finalCalendar = Calendar.getInstance().apply {
                        set(year, month, day, timePickerState.hour, timePickerState.minute, 0)
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