package com.juagosin.coffeetracker.data.mapper

import com.juagosin.coffeetracker.data.dao.DayStats
import com.juagosin.coffeetracker.domain.model.stats.DailyCoffeeStat
import kotlin.collections.map

fun DayStats.toDomain(): DailyCoffeeStat {
    return DailyCoffeeStat.fromDayStats(this)
}

fun List<DayStats>.toDomainDailyStats(): List<DailyCoffeeStat> {
    return this.map { it.toDomain() }
}