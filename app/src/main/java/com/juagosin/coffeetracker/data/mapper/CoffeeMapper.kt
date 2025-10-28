package com.juagosin.coffeetracker.data.mapper

import com.juagosin.coffeetracker.data.entity.CoffeeEntity
import com.juagosin.coffeetracker.domain.model.Coffee

fun CoffeeEntity.toDomain(): Coffee {
    return Coffee(
        id = id,
        type = coffeeType,
        date =timestamp
    )
}

fun Coffee.toEntity(): CoffeeEntity {
    return CoffeeEntity(
        id = id,
        coffeeType = type,
        timestamp = date
    )
}

fun List<CoffeeEntity>.toDomain(): List<Coffee> {
    return map { it.toDomain() }
}
