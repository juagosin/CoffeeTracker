package com.juagosin.coffeetracker.data.mapper

import android.R.attr.label
import com.juagosin.coffeetracker.data.dao.CoffeeTypeCount
import com.juagosin.coffeetracker.data.dao.DayStats
import com.juagosin.coffeetracker.domain.model.CoffeeType
import com.juagosin.coffeetracker.domain.model.stats.AllTimeTypeStats
import com.juagosin.coffeetracker.domain.model.stats.DailyCoffeeStat
import kotlin.collections.map
import kotlin.collections.mapIndexed

fun DayStats.toDomain(): DailyCoffeeStat {
    return DailyCoffeeStat.fromDayStats(this)
}

fun List<DayStats>.toDomainDailyStats(): List<DailyCoffeeStat> {
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