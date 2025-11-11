package com.calleserpis.coffeetracker.data.mapper

import com.calleserpis.coffeetracker.data.entity.CoffeeEntity
import com.calleserpis.coffeetracker.domain.model.Coffee
import com.calleserpis.coffeetracker.domain.model.CoffeeType

fun CoffeeEntity.toDomain(): Coffee {
    return Coffee(
        id = this.id,
        type = CoffeeType.fromString(this.coffeeType),
        timestamp = this.timestamp,
        notes = this.notes
    )
}

fun Coffee.toEntity(): CoffeeEntity {
    return CoffeeEntity(
        id = id,
        coffeeType = this.type.name,
        timestamp = this.timestamp,
        notes = this.notes
    )
}

fun List<CoffeeEntity>.toDomain(): List<Coffee> {
    return map { it.toDomain() }
}
