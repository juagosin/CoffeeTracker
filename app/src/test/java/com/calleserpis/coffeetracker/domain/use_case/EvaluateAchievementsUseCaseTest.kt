package com.calleserpis.coffeetracker.domain.use_case

import com.calleserpis.coffeetracker.domain.model.Coffee
import com.calleserpis.coffeetracker.domain.model.CoffeeType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneId

class EvaluateAchievementsUseCaseTest {
    private val useCase = EvaluateAchievementsUseCase()

    @Test
    fun `restored history unlocks eligible total achievements`() {
        val coffees = List(2001) { index -> coffee(index) }

        val achievements = useCase(
            coffees = coffees,
            unlockedAchievementIds = emptySet(),
            unlockedAt = 123L
        )

        assertTrue(achievements.any { it.id == "coffee_2001" })
        assertEquals(123L, achievements.first { it.id == "coffee_2001" }.unlockedAt)
    }

    @Test
    fun `already unlocked achievements are not returned again`() {
        val achievements = useCase(
            coffees = List(2001) { index -> coffee(index) },
            unlockedAchievementIds = setOf("coffee_2001")
        )

        assertFalse(achievements.any { it.id == "coffee_2001" })
    }

    @Test
    fun `repeated evaluation is idempotent`() {
        val coffees = List(50) { index -> coffee(index) }
        val firstEvaluation = useCase(coffees, emptySet())
        val secondEvaluation = useCase(coffees, firstEvaluation.mapTo(mutableSetOf()) { it.id })

        assertTrue(firstEvaluation.any { it.id == "coffee_50" })
        assertTrue(secondEvaluation.isEmpty())
    }

    @Test
    fun `restored history unlocks daily achievements from a past day`() {
        val timestamp = LocalDate.of(2020, 1, 2)
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
        val coffees = List(5) { index -> coffee(index, timestamp) }

        val achievements = useCase(coffees, emptySet())

        assertTrue(achievements.any { it.id == "daily_5" })
    }

    private fun coffee(id: Int, timestamp: Long = 0L) = Coffee(
        id = id,
        type = CoffeeType.ESPRESSO,
        timestamp = timestamp
    )
}
