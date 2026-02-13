package com.calleserpis.coffeetracker.domain.use_case

import com.calleserpis.coffeetracker.domain.model.Achievement
import com.calleserpis.coffeetracker.domain.repository.CoffeeRepository

class AddAchievementUseCase(
    private val repository: CoffeeRepository
) {
    suspend operator fun invoke(achievement: Achievement) {
        repository.insertAchievement(achievement)
    }
}