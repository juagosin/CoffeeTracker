package com.calleserpis.coffeetracker.domain.use_case

import com.calleserpis.coffeetracker.domain.model.Achievement
import com.calleserpis.coffeetracker.domain.model.AchievementDefinition
import com.calleserpis.coffeetracker.domain.model.Coffee
import javax.inject.Inject

class EvaluateAchievementsUseCase @Inject constructor() {
    operator fun invoke(
        coffees: List<Coffee>,
        unlockedAchievementIds: Set<String>,
        unlockedAt: Long = System.currentTimeMillis()
    ): List<Achievement> {
        return AchievementDefinition.ALL
            .filter { definition ->
                definition.id !in unlockedAchievementIds && definition.checkCondition(coffees)
            }
            .map { definition ->
                Achievement(
                    id = definition.id,
                    type = definition.type,
                    threshold = definition.threshold,
                    unlockedAt = unlockedAt,
                    isUnlocked = true
                )
            }
    }
}
