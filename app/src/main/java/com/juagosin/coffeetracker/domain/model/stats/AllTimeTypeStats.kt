package com.juagosin.coffeetracker.domain.model.stats

import com.juagosin.coffeetracker.data.dao.CoffeeTypeCount
import com.juagosin.coffeetracker.domain.model.CoffeeType

data class AllTimeTypeStats(
    val coffeeType: CoffeeType,
    val value: Float
){

    companion object{
        fun fromHistoryStats(historyStats: CoffeeTypeCount): AllTimeTypeStats{
            return AllTimeTypeStats(
                coffeeType = historyStats.coffeeType,
                value = historyStats.value
            )
        }
    }
}