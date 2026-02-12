package com.calleserpis.coffeetracker.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.calleserpis.coffeetracker.R

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
        checkCondition = { coffees -> coffees.size >= 1 }

    )

    companion object {
        val ALL = listOf(
            FirstCoffee
        )
    }
}



