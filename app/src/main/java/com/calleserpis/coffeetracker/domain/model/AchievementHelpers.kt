package com.calleserpis.coffeetracker.domain.model

import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneId
import java.time.temporal.ChronoUnit

object AchievementHelpers {

    fun Long.toLocalDate(): LocalDate {
        return Instant.ofEpochMilli(this)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }

    fun Long.toLocalDateTime(): LocalDateTime {
        return Instant.ofEpochMilli(this)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    }
//VAlidamos que las fechas sean recientes para que no hagan "Trampa"
    private fun LocalDate.isRecent(maxDaysOld: Int): Boolean {
        val today = LocalDate.now()
        val daysDifference = ChronoUnit.DAYS.between(this, today)
        return daysDifference in 0..maxDaysOld
    }
    private fun LocalDateTime.isRecent(maxDaysOld: Int): Boolean {
        val today = LocalDateTime.now()
        val daysDifference = ChronoUnit.DAYS.between(this, today)
        return daysDifference in 0..maxDaysOld
    }
    private fun LocalDate.isSpecialDateRecent(
        day: Int,
        month: Month,
        maxDaysOld: Int = 3
    ): Boolean {
        return dayOfMonth == day &&
                this.month == month &&
                isRecent(maxDaysOld)
    }

    private fun LocalDateTime.isSpecialDateTimeRecent(
        hour: Int,
        minute: Int,
        maxDaysOld: Int = 1
    ): Boolean {
        return this.hour == hour &&
                this.minute == minute &&
                isRecent(maxDaysOld)
    }
    fun LocalDate.isChristmas(maxDaysOld: Int = 3): Boolean {
        return isSpecialDateRecent(25, Month.DECEMBER, maxDaysOld)
    }

    fun LocalDateTime.isNewYear(maxDaysOld: Int = 3): Boolean {
        val isCorrectDateTime = dayOfMonth == 1 &&
                month == Month.JANUARY &&
                hour < 6

        return isCorrectDateTime && toLocalDate().isRecent(maxDaysOld)
    }
    fun LocalDateTime.isLastMinute(maxDaysOld: Int = 1): Boolean {
        return isSpecialDateTimeRecent( 23, 59, maxDaysOld)
    }

    fun LocalDate.isValentines(maxDaysOld: Int = 3): Boolean {
        return isSpecialDateRecent(14, Month.FEBRUARY, maxDaysOld)
    }

    fun LocalDate.isHalloween(maxDaysOld: Int = 3): Boolean {
        return isSpecialDateRecent(31, Month.OCTOBER, maxDaysOld)
    }
    fun LocalDate.LeapYear(maxDaysOld: Int = 3): Boolean {
        return isSpecialDateRecent(29, Month.FEBRUARY, maxDaysOld)
    }

    fun LocalDateTime.isMondayMorning(maxDaysOld: Int = 3): Boolean {

        val isCorrectDateTime = dayOfWeek == DayOfWeek.MONDAY && hour < 6

        return isCorrectDateTime && toLocalDate().isRecent(maxDaysOld)
    }

    fun LocalDateTime.isMorning(maxDaysOld: Int = 7): Boolean {

        val isCorrectDateTime = hour < 9

        return isCorrectDateTime && toLocalDate().isRecent(maxDaysOld)
    }

    fun LocalDate.isSpecificDate(day: Int, month: Month): Boolean {
        return dayOfMonth == day && this.month == month
    }
    fun LocalDateTime.isSpecificHour(hour: Int, minute: Int): Boolean {
        return this.hour == hour && this.minute == minute
    }

    fun LocalDate.isInDateRange(startDay: Int, endDay: Int, month: Month): Boolean {
        return this.month == month && dayOfMonth in startDay..endDay
    }

    fun List<Long>.hasConsecutiveDaysStreak(days: Int): Boolean {
        if (this.isEmpty()) return false

        val dates = this.map { it.toLocalDate() }.distinct().sorted()
        if (dates.size < days) return false

        var currentStreak = 1
        var maxStreak = 1

        for (i in 1 until dates.size) {
            val daysDiff = ChronoUnit.DAYS.between(dates[i - 1], dates[i])

            if (daysDiff == 1L) {
                currentStreak++
                maxStreak = maxOf(maxStreak, currentStreak)
            } else {
                currentStreak = 1
            }
        }

        return maxStreak >= days
    }


    fun List<Long>.hasRestDayAfterStreak(streakDays: Int, windowDays: Int = streakDays + 10): Boolean {
        val today = LocalDate.now()
        val cutoffDate = today.minusDays(windowDays.toLong())

        // Filtrar solo las fechas dentro de la ventana de tiempo
        val dates = this
            .map { it.toLocalDate() }
            .filter { it >= cutoffDate } // Solo fechas recientes
            .distinct()
            .sorted()

        if (dates.size < streakDays) return false

        // Buscar rachas de N días seguidas de un día sin café
        for (i in (streakDays - 1) until dates.size) {
            val potentialStreak = dates.subList(i - streakDays + 1, i + 1)

            // Verificar que sean N días consecutivos
            val isConsecutive = (1 until potentialStreak.size).all { j ->
                ChronoUnit.DAYS.between(potentialStreak[j - 1], potentialStreak[j]) == 1L
            }

            if (isConsecutive) {
                // Verificar que el siguiente día tenga un hueco (2 días = 1 día sin café)
                if (i < dates.size - 1) {
                    val gap = ChronoUnit.DAYS.between(dates[i], dates[i + 1])
                    if (gap == 2L) return true
                } else {
                    // Si es el último, verificar que hoy no haya café
                    val lastDate = dates[i]
                    if (lastDate.plusDays(1) < today) return true
                }
            }
        }

        return false
    }

    fun List<Long>.hasSameTimeInDifferentDays(
        occurrences: Int,
        exactMinute: Boolean = true,
        debug: Boolean = false
    ): Boolean {
        if (this.isEmpty()) return false

        val coffeesWithDateTime = this
            .map { timestamp ->
                val dateTime = timestamp.toLocalDateTime()
                val date = dateTime.toLocalDate()
                val time = if (exactMinute) {
                    "${dateTime.hour.toString().padStart(2, '0')}:${dateTime.minute.toString().padStart(2, '0')}"
                } else {
                    dateTime.hour.toString().padStart(2, '0')
                }
                Triple(date, time, dateTime)
            }
            .distinctBy { (date, time, _) -> "$date-$time" }

        if (debug) {
            println("=== SAME TIME DEBUG ===")
            println("Total coffees: ${this.size}")
            println("Unique date-time combinations: ${coffeesWithDateTime.size}")
            println("Required occurrences: $occurrences")
        }

        if (coffeesWithDateTime.size < occurrences) {
            if (debug) println("Not enough unique date-time combinations")
            return false
        }

        val timeGroups = coffeesWithDateTime
            .groupBy { (_, time, _) -> time }
            .mapValues { entry ->
                entry.value.map { (date, _, _) -> date }.distinct()
            }

        if (debug) {
            println("\n=== TIME GROUPS ===")
            timeGroups.forEach { (time, dates) ->
                println("Time $time: ${dates.size} different days")
                if (dates.size >= 3) {
                    println("  Dates: ${dates.take(5).joinToString(", ")}${if (dates.size > 5) "..." else ""}")
                }
            }
            println("\nMax days for any time: ${timeGroups.values.maxOfOrNull { it.size } ?: 0}")
        }

        val result = timeGroups.values.any { dates -> dates.size >= occurrences }

        if (debug) {
            println("Result: $result")
            println("======================\n")
        }

        return result
    }
}