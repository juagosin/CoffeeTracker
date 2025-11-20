package com.calleserpis.coffeetracker.domain.model.stats

import androidx.compose.ui.text.capitalize
import com.calleserpis.coffeetracker.data.dao.MonthStats
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

data class MonthlyCoffeeStats(
    val month: String,
    val value: Int
) {
    private val yearMonth: YearMonth?
        get() = try {
            YearMonth.parse(month)
        } catch (e: Exception) {
            null
        }

    fun getMonthAbbreviation(locale: Locale = Locale.getDefault()): String {
        return yearMonth?.month?.getDisplayName(TextStyle.SHORT, locale) ?: month
    }

    fun getMonthName(locale: Locale = Locale.getDefault()): String {
        return yearMonth?.month?.getDisplayName(TextStyle.FULL, locale)?.capitalize() ?: month
    }

    companion object {
        fun fromMonthStats(monthStats: MonthStats): MonthlyCoffeeStats {
            return MonthlyCoffeeStats(
                month = monthStats.month,
                value = monthStats.count
            )
        }
    }
}