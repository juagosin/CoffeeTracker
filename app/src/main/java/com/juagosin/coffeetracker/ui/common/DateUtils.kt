package com.juagosin.readingAPP.presentation.common

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


// util/DateUtils.kt
object DateUtils {

    fun getCurrentTimeMillis(): Long = System.currentTimeMillis()

    fun formatDate(
        timestamp: Long,
        pattern: String = "dd/MM/yyyy",
        locale: Locale = Locale.getDefault()
    ): String {
        val formatter = SimpleDateFormat(pattern, locale)
        return formatter.format(Date(timestamp))
    }

    fun formatDateTime(
        timestamp: Long,
        pattern: String = "dd/MM/yyyy HH:mm",
        locale: Locale = Locale.getDefault()
    ): String {
        val formatter = SimpleDateFormat(pattern, locale)
        return formatter.format(Date(timestamp))
    }

    // Otros formatos comunes
    fun formatTime(timestamp: Long): String =
        formatDate(timestamp, "HH:mm")

    fun formatShortDate(timestamp: Long): String =
        formatDate(timestamp, "dd/MM/yy")

    fun formatLongDate(timestamp: Long): String =
        formatDate(timestamp, "EEEE, dd 'de' MMMM 'de' yyyy", Locale("es", "ES"))
}