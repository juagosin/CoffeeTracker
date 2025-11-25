package com.calleserpis.readingAPP.presentation.common

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


// util/DateUtils.kt
object DateUtils {

    fun getCurrentTimeMillis(): Long = System.currentTimeMillis()

    fun formatDate(timeInMillis: Long, pattern: String): String {
        return try {
            val sdf = SimpleDateFormat(pattern, Locale.getDefault())
            // IMPORTANTE: Usar zona horaria del sistema
            sdf.timeZone = TimeZone.getDefault()
            sdf.format(Date(timeInMillis))
        } catch (e: Exception) {
            ""
        }
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

}