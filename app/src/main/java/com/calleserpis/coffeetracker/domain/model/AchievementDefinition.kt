package com.calleserpis.coffeetracker.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.calleserpis.coffeetracker.R
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

sealed class AchievementDefinition (
    val id: String,
    val type: AchievementType,
    val threshold: Int,
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int,
    @DrawableRes val iconRes: Int,
    val checkCondition: (List<Coffee>) -> Boolean
){
    data object FirstCoffee : AchievementDefinition(
        id = "first_coffee",
        type = AchievementType.FIRST_COFFEE,
        threshold = 1,
        titleRes = R.string.achievement_first_coffee_title,
        descriptionRes = R.string.achievement_first_coffee_description,
        iconRes = R.drawable.ic_achievement_first,
        checkCondition = { coffees -> coffees.isNotEmpty() }

    )
    data object Coffee50  : AchievementDefinition(
        id = "coffee_50",
        type = AchievementType.TOTAL_COFFEES_50,
        threshold = 1,
        titleRes = R.string.achievement_fifty_coffee_title,
        descriptionRes = R.string.achievement_fifty_coffee_description,
        iconRes = R.drawable.ic_achievement_fifty,
        checkCondition = { coffees -> coffees.size >= 50 }

    )
    data object Coffee100  : AchievementDefinition(
        id = "coffee_100",
        type = AchievementType.TOTAL_COFFEES_100,
        threshold = 1,
        titleRes = R.string.achievement_hundred_coffee_title,
        descriptionRes = R.string.achievement_hundred_coffee_description,
        iconRes = R.drawable.ic_achievement_hundred,
        checkCondition = { coffees -> coffees.size >= 100 }

    )
    data object DailyAddict5  : AchievementDefinition(
        id = "daily_5",
        type = AchievementType.DAILY_COFFEES_5,
        threshold = 1,
        titleRes = R.string.achievement_daily_5_coffee_title,
        descriptionRes = R.string.achievement_daily_5_coffee_description,
        iconRes = R.drawable.ic_achievement_daily_5,
        checkCondition = { coffees ->
            val today = LocalDate.now()
            coffees.count {
                Instant.ofEpochMilli(it.timestamp)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate() == today
            } >= 5
        }

    )

    companion object {
        val ALL = listOf(
            FirstCoffee,
            Coffee50,
            Coffee100,
            DailyAddict5
        )
    }
}



