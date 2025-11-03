package com.juagosin.coffeetracker.domain.model.stats

import com.juagosin.coffeetracker.data.dao.DayStats
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class DailyCoffeeStat(
    val date: String,          // "YYYY-MM-DD"
    val count: Int,
    val dayOfWeek: String,     // "Lunes", "Martes", etc.
    val formattedDate: String  // "01 Ene"
) {
    companion object {
        fun fromDayStats(dayStats: DayStats): DailyCoffeeStat {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = dateFormat.parse(dayStats.day) ?: Date()

            val dayOfWeekFormat = SimpleDateFormat("EEEE", Locale("es", "ES"))
            val shortDateFormat = SimpleDateFormat("dd MMM", Locale("es", "ES"))

            return DailyCoffeeStat(
                date = dayStats.day,
                count = dayStats.count,
                dayOfWeek = dayOfWeekFormat.format(date).capitalize().substring(0, 3),
                formattedDate = shortDateFormat.format(date)
            )
        }
    }
}