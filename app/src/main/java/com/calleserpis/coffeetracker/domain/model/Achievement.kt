package com.calleserpis.coffeetracker.domain.model

data class Achievement(
    val id: String,
    val type: AchievementType,
    val threshold: Int,
    val isUnlocked: Boolean = false,
    val unlockedAt: Long? = null,
    val progress: Int = 0
)

enum class AchievementType {
    FIRST_COFFEE,
    TOTAL_COFFEES_1,
    TOTAL_COFFEES_50,
    TOTAL_COFFEES_100,
    TOTAL_COFFEES_500,
    TOTAL_COFFEES_666,
    TOTAL_COFFEES_2001,
    WEEKLY_COFFEES_20,
    DAILY_COFFEES,
    CHRISTMAS_COFFEE,
    NEW_YEAR_COFFEE,
    VALENTINES_COFFEE,
    HALLOWEEN_COFFEE,
    LEAP_YEAR_COFFEE,
    MONDAY_MORNING_COFFEE,
    STREAK,
    SPECIAL_CONDITION,
    PRECISION,
    SPECIAL_DATE,
    SPECIAL_HOUR,
    COFFEE_TYPE;

    companion object {
        fun fromString(value: String): AchievementType {
            return entries.find { it.name == value } ?: FIRST_COFFEE
        }
    }
}
