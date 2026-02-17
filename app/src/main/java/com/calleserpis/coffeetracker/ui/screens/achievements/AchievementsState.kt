package com.calleserpis.coffeetracker.ui.screens.achievements

import com.calleserpis.coffeetracker.domain.model.Achievement

data class AchievementsState (
    val allAchievements: List<Achievement> = emptyList(),
)