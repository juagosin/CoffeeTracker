package com.calleserpis.coffeetracker.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.calleserpis.coffeetracker.R
import com.calleserpis.coffeetracker.domain.model.AchievementHelpers.LeapYear
import com.calleserpis.coffeetracker.domain.model.AchievementHelpers.hasConsecutiveDaysStreak
import com.calleserpis.coffeetracker.domain.model.AchievementHelpers.hasRestDayAfterStreak
import com.calleserpis.coffeetracker.domain.model.AchievementHelpers.hasSameTimeInDifferentDays
import com.calleserpis.coffeetracker.domain.model.AchievementHelpers.isChristmas
import com.calleserpis.coffeetracker.domain.model.AchievementHelpers.isHalloween
import com.calleserpis.coffeetracker.domain.model.AchievementHelpers.isLastMinute
import com.calleserpis.coffeetracker.domain.model.AchievementHelpers.isMondayMorning
import com.calleserpis.coffeetracker.domain.model.AchievementHelpers.isMorning
import com.calleserpis.coffeetracker.domain.model.AchievementHelpers.isNewYear
import com.calleserpis.coffeetracker.domain.model.AchievementHelpers.isValentines
import com.calleserpis.coffeetracker.domain.model.AchievementHelpers.toLocalDate
import com.calleserpis.coffeetracker.domain.model.AchievementHelpers.toLocalDateTime
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

sealed class AchievementDefinition(
    val id: String,
    val type: AchievementType,
    val threshold: Int,
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int,
    @DrawableRes val iconRes: Int,
    val checkCondition: (List<Coffee>) -> Boolean
) {
    data object FirstCoffee : AchievementDefinition(
        id = "first_coffee",
        type = AchievementType.FIRST_COFFEE,
        threshold = 1,
        titleRes = R.string.achievement_first_coffee_title,
        descriptionRes = R.string.achievement_first_coffee_description,
        iconRes = R.drawable.ic_achievement_first,
        checkCondition = { coffees -> coffees.isNotEmpty() }

    )

    data object Coffee50 : AchievementDefinition(
        id = "coffee_50",
        type = AchievementType.TOTAL_COFFEES_50,
        threshold = 1,
        titleRes = R.string.achievement_fifty_coffee_title,
        descriptionRes = R.string.achievement_fifty_coffee_description,
        iconRes = R.drawable.ic_achievement_fifty,
        checkCondition = { coffees -> coffees.size >= 50 }

    )

    data object Coffee100 : AchievementDefinition(
        id = "coffee_100",
        type = AchievementType.TOTAL_COFFEES_100,
        threshold = 1,
        titleRes = R.string.achievement_hundred_coffee_title,
        descriptionRes = R.string.achievement_hundred_coffee_description,
        iconRes = R.drawable.ic_achievement_hundred,
        checkCondition = { coffees -> coffees.size >= 100 }

    )

    data object Coffee500 : AchievementDefinition(
        id = "coffee_500",
        type = AchievementType.TOTAL_COFFEES_500,
        threshold = 1,
        titleRes = R.string.achievement_fifty_hundred_coffee_title,
        descriptionRes = R.string.achievement_fifty_hundred_coffee_description,
        iconRes = R.drawable.ic_achievement_fifty_hundred,
        checkCondition = { coffees -> coffees.size >= 500 }

    )
    data object Coffee666 : AchievementDefinition(
        id = "coffee_666",
        type = AchievementType.TOTAL_COFFEES_666,
        threshold = 1,
        titleRes = R.string.achievement_666_coffee_title,
        descriptionRes = R.string.achievement_666_coffee_description,
        iconRes = R.drawable.ic_achievement_666,
        checkCondition = { coffees -> coffees.size >= 666 }

    )
    data object Coffee2001 : AchievementDefinition(
        id = "coffee_2001",
        type = AchievementType.TOTAL_COFFEES_2001,
        threshold = 1,
        titleRes = R.string.achievement_spaceodyssey_coffee_title,
        descriptionRes = R.string.achievement_spaceodyssey_coffee_description,
        iconRes = R.drawable.ic_achievement_valentine, //TODO change to correct icon
        checkCondition = { coffees -> coffees.size >= 2001 }

    )
    data object DailyAddict5 : AchievementDefinition(
        id = "daily_5",
        type = AchievementType.DAILY_COFFEES,
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
    data object DailyAddict10 : AchievementDefinition(
        id = "daily_10",
        type = AchievementType.DAILY_COFFEES,
        threshold = 1,
        titleRes = R.string.achievement_daily_10_coffee_title,
        descriptionRes = R.string.achievement_daily_10_coffee_description,
        iconRes = R.drawable.ic_achievement_valentine, //TODO change to correct icon
        checkCondition = { coffees ->
            val today = LocalDate.now()
            coffees.count {
                Instant.ofEpochMilli(it.timestamp)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate() == today
            } >= 10
        }
    )

    data object ChristmasCoffee : AchievementDefinition(
        id = "christmas_coffee",
        type = AchievementType.SPECIAL_DATE,
        threshold = 1,
        titleRes = R.string.achievement_christmas_title,
        descriptionRes = R.string.achievement_christmas_desc,
        iconRes = R.drawable.ic_achievement_christmas,
        checkCondition = { coffees ->
            coffees.any { it.timestamp.toLocalDate().isChristmas() }
        }
    )
    data object NewYearCoffee : AchievementDefinition(
        id = "new_year_coffee",
        type = AchievementType.SPECIAL_DATE,
        threshold = 1,
        titleRes = R.string.achievement_new_year_title,
        descriptionRes = R.string.achievement_new_year_desc,
        iconRes = R.drawable.ic_achievement_valentine, //TODO change to correct icon
        checkCondition = { coffees ->
            coffees.any { it.timestamp.toLocalDateTime().isNewYear() }
        }
    )
    data object ValentinesCoffee : AchievementDefinition(
        id = "valentines_coffee",
        type = AchievementType.SPECIAL_DATE,
        threshold = 1,
        titleRes = R.string.achievement_valentines_title,
        descriptionRes = R.string.achievement_valentines_desc,
        iconRes = R.drawable.ic_achievement_valentine,
        checkCondition = { coffees ->
            coffees.any { it.timestamp.toLocalDate().isValentines() }
        }
    )
    data object HalloweenCoffee : AchievementDefinition(
        id = "halloween_coffee",
        type = AchievementType.SPECIAL_DATE,
        threshold = 1,
        titleRes = R.string.achievement_halloween_title,
        descriptionRes = R.string.achievement_halloween_desc,
        iconRes = R.drawable.ic_achievement_valentine, //TODO change to correct icon
        checkCondition = { coffees ->
            coffees.any { it.timestamp.toLocalDate().isHalloween() }
        }
    )
    data object LeapYearCoffee : AchievementDefinition(
        id = "leap_year_coffee",
        type = AchievementType.SPECIAL_DATE,
        threshold = 1,
        titleRes = R.string.achievement_leap_year_title,
        descriptionRes = R.string.achievement_leap_year_desc,
        iconRes = R.drawable.ic_achievement_valentine, //TODO change to correct icon
        checkCondition = { coffees ->
            coffees.any { it.timestamp.toLocalDate().LeapYear() }
        }
    )
    data object MondayMorning : AchievementDefinition(
        id = "monday_morning_coffee",
        type = AchievementType.MONDAY_MORNING_COFFEE,
        threshold = 1,
        titleRes = R.string.achievement_monday_morning_title,
        descriptionRes = R.string.achievement_monday_morning_desc,
        iconRes = R.drawable.ic_achievement_valentine, //TODO change to correct icon
        checkCondition = { coffees ->
            coffees.any { it.timestamp.toLocalDateTime().isMondayMorning() }
        }
    )
    data object DecafMorning : AchievementDefinition(
        id = "decaf_morning",
        type = AchievementType.SPECIAL_CONDITION,
        threshold = 5,
        titleRes = R.string.achievement_decaf_morning_title,
        descriptionRes = R.string.achievement_decaf_morning_desc,
        iconRes = R.drawable.ic_achievement_valentine, //TODO change to correct icon
        checkCondition = { coffees ->
            coffees.count {
                it.type == CoffeeType.DESCAFEINADO &&
                        it.timestamp.toLocalDateTime().isMorning(10)
            } >= 7
        }
    )
    data object LastMinute : AchievementDefinition(
        id = "last_minute_coffee",
        type = AchievementType.SPECIAL_HOUR,
        threshold = 1,
        titleRes = R.string.achievement_last_minute_title,
        descriptionRes = R.string.achievement_last_minute_desc,
        iconRes = R.drawable.ic_achievement_valentine, //TODO change to correct icon
        checkCondition = { coffees ->
            coffees.any { it.timestamp.toLocalDateTime().isLastMinute() }
        }
    )
    data object YearStreak : AchievementDefinition(
        id = "year_streak",
        type = AchievementType.STREAK,
        threshold = 365,
        titleRes = R.string.achievement_year_streak_title,
        descriptionRes = R.string.achievement_year_streak_desc,
        iconRes = R.drawable.ic_achievement_valentine, //TODO change to correct icon
        checkCondition = { coffees ->
            val timestamps = coffees.map { it.timestamp }
            timestamps.hasConsecutiveDaysStreak(365)
        }
    )
    data object PerfectDetox : AchievementDefinition(
        id = "perfect_detox",
        type = AchievementType.SPECIAL_CONDITION,
        threshold = 30,
        titleRes = R.string.achievement_perfect_detox_title,
        descriptionRes = R.string.achievement_perfect_detox_desc,
        iconRes = R.drawable.ic_achievement_valentine, //TODO change to correct icon
        checkCondition = { coffees ->
            coffees.map { it.timestamp }.hasRestDayAfterStreak(streakDays = 30,
                windowDays = 35)
        }
    )
    data object SwissWatch : AchievementDefinition(
        id = "swiss_watch",
        type = AchievementType.PRECISION,
        threshold = 7,
        titleRes = R.string.achievement_swiss_watch_title,
        descriptionRes = R.string.achievement_swiss_watch_desc,
        iconRes = R.drawable.ic_achievement_valentine, //TODO change to correct icon
        checkCondition = { coffees ->
            coffees.map { it.timestamp }.hasSameTimeInDifferentDays(
                occurrences = 7,
                exactMinute = true,
                debug = false
            )
        }
    )
    // Probar todos los tipos
    data object CoffeeExplorer : AchievementDefinition(
        id = "coffee_explorer",
        type = AchievementType.COFFEE_TYPE,
        threshold = CoffeeType.entries.size,
        titleRes = R.string.achievement_explorer_title,
        descriptionRes = R.string.achievement_explorer_desc,
        iconRes = R.drawable.ic_achievement_valentine, //TODO change to correct icon
        checkCondition = { coffees ->
            val triedTypes = coffees.map { it.type }.distinct()
            triedTypes.size >= CoffeeType.entries.size
        }
    )
    companion object {
        val ALL = listOf(
            FirstCoffee,
            Coffee50,
            Coffee100,
            Coffee500,
            Coffee666,
            Coffee2001,
            DailyAddict5,
            DailyAddict10,
            ChristmasCoffee,
            NewYearCoffee,
            ValentinesCoffee,
            HalloweenCoffee,
            LeapYearCoffee,
            YearStreak,
            PerfectDetox,
            MondayMorning,
            SwissWatch,
            LastMinute,
            CoffeeExplorer,
            DecafMorning
        )
    }
}



