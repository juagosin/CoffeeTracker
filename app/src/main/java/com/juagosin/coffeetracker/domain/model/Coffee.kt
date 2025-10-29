package com.juagosin.coffeetracker.domain.model

data class Coffee (
    val id: Int,
    val type: CoffeeType,
    val timestamp: Long,
    val notes: String? = null
)

enum class CoffeeType(val displayName: String, val emoji: String) {

    CORTADO("Cortado", "☕"),
    LATTE("Con Leche", "🥛"),
    ESPRESSO("Corto", "☕"),
    LUNGO("Largo", "☕"),
    AMERICANO("Americano", "☕"),

    CAPPUCCINO("Capuchino", "☕"),

    MACCHIATO("Manchado", "☕"),
    MOCHA("Moka", "🍫"),
    DESCAFEINADO("Descafeinado", "☕");

    companion object {
        fun fromString(value: String): CoffeeType {
            return entries.find { it.name == value } ?: ESPRESSO
        }
    }
}