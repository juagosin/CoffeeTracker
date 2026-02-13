package com.calleserpis.coffeetracker.data.mapper

import com.calleserpis.coffeetracker.data.entity.AchievementEntity
import com.calleserpis.coffeetracker.domain.model.Achievement
import com.calleserpis.coffeetracker.domain.model.AchievementType


fun AchievementEntity.toDomain(): Achievement {
    return Achievement(
        id = this.id,
        type = AchievementType.fromString(this.id),
        unlockedAt = this.unlockedAt,
        progress = this.progress,
        threshold = 0

    )
}

fun Achievement.toEntity(): AchievementEntity {
    return AchievementEntity(
        id = this.id,
        unlockedAt = this.unlockedAt,
        progress = this.progress
    )
}
fun List<AchievementEntity>.toDomain(): List<Achievement> {
    return map { it.toDomain() }
}