package com.juagosin.coffeetracker.data.mapper

import com.juagosin.coffeetracker.data.dao.CoffeeTypeCount
import com.juagosin.coffeetracker.data.dao.DayStats
import com.juagosin.coffeetracker.data.dao.MonthStats
import com.juagosin.coffeetracker.domain.model.stats.AllTimeTypeStats
import com.juagosin.coffeetracker.domain.model.stats.DailyCoffeeStat
import com.juagosin.coffeetracker.domain.model.stats.MonthlyCoffeeStats

fun DayStats.toDomain(): DailyCoffeeStat {
    return DailyCoffeeStat.fromDayStats(this)
}

fun List<DayStats>.toDomainDailyStats(): List<DailyCoffeeStat> {
    return this.map { it.toDomain() }
}

fun MonthStats.toDomain(): MonthlyCoffeeStats{
    return MonthlyCoffeeStats.fromMonthStats(this)
}

fun List<MonthStats>.toDomainMonthlyStats(): List<MonthlyCoffeeStats> {
    return this.map { it.toDomain() }
}

fun List<CoffeeTypeCount>.toTypeHistory(): List<AllTimeTypeStats> {

    return this.mapIndexed { index, typeCount ->
        AllTimeTypeStats(
            coffeeType = typeCount.coffeeType,
            value = typeCount.value

        )
    }
}