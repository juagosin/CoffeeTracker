package com.juagosin.coffeetracker.domain.model.stats

import com.juagosin.coffeetracker.data.dao.MonthStats

data class MonthlyCoffeeStats(
    val month: String,
    val value: Int
) {
    val monthAbbreviation: String
        get() = formatMonthAbbreviation(month)

    companion object {
        private fun formatMonthAbbreviation(yearMonth: String): String {
            val monthNumber = yearMonth.split("-").getOrNull(1)?.toIntOrNull() ?: return yearMonth
            return when (monthNumber) {
                1 -> "Ene"
                2 -> "Feb"
                3 -> "Mar"
                4 -> "Abr"
                5 -> "May"
                6 -> "Jun"
                7 -> "Jul"
                8 -> "Ago"
                9 -> "Sep"
                10 -> "Oct"
                11 -> "Nov"
                12 -> "Dic"
                else -> yearMonth
            }
        }

        fun fromMonthStats(monthStats: MonthStats): MonthlyCoffeeStats {
            return MonthlyCoffeeStats(
                month = monthStats.month,
                value = monthStats.count
            )
        }
    }
}