package com.calleserpis.coffeetracker.domain.use_case

import com.calleserpis.coffeetracker.domain.model.Achievement
import com.calleserpis.coffeetracker.domain.repository.CoffeeRepository
import kotlinx.coroutines.flow.Flow

class GetAllAchievementsUseCase (
    private val coffeeRepository: CoffeeRepository
) {
    operator fun invoke(): Flow<List<Achievement>> {
        return coffeeRepository.getAllUnlockedAchievements()

    }
}