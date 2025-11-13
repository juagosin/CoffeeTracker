package com.calleserpis.coffeetracker.domain.model

import TimeDuration
import androidx.compose.ui.graphics.Color
import timeSinceNow

data class Coffee (
    val id: Int,
    val type: CoffeeType,
    val timestamp: Long,
    val notes: String? = null,
    val price: Double? = null
)

enum class CoffeeType(val displayName: String, val emoji: String,val color: Color) {

    CORTADO("Cortado", "☕",Color(0xFF3B82F6)),
    LATTE("Con Leche", "🥛", Color(0xFFF59E0B)),
    ESPRESSO("Solo", "☕", Color(0xFFEC4899)),
    LUNGO("Largo", "☕", Color(0xFF8B5A3C)),
    CAPPUCCINO("Capuchino", "☕", Color(0xFF6366F1)),
    DESCAFEINADO("Descafeinado", "☕", Color(0xFF10B981)),
    CREMAET("Cremaet", "☕", Color(0xFFFF5722)),
    OTROS("Otros", "🍫", Color(0xFFEF4444));

    companion object {
        fun fromString(value: String): CoffeeType {
            return entries.find { it.name == value } ?: ESPRESSO
        }
    }
}
// Extensiones para obtener TimeDuration desde Coffee
fun Coffee.getTimeSinceNow(): TimeDuration {
    return this.timestamp.timeSinceNow()
}

fun Coffee.getRelativeTime(): String {
    return this.timestamp.timeSinceNow().toRelativeString()
}
