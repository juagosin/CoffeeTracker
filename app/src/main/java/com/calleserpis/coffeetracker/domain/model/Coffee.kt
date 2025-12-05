package com.calleserpis.coffeetracker.domain.model

import TimeDuration
import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.calleserpis.coffeetracker.R
import timeSinceNow

data class Coffee (
    val id: Int,
    val type: CoffeeType,
    val timestamp: Long,
    val notes: String? = null,
    val price: Double? = null
)

enum class CoffeeType(
    @StringRes val displayNameRes: Int,
    val emoji: String,
    val color: Color
) {

    CORTADO(R.string.coffee_type_cortado, "☕",Color(0xFF3B82F6)),
    LATTE(R.string.coffee_type_latte, "🥛", Color(0xFFF59E0B)),
    ESPRESSO(R.string.coffee_type_espresso, "☕", Color(0xFFEC4899)),
    LUNGO(R.string.coffee_type_lungo, "☕", Color(0xFF8B5A3C)),
    CAPPUCCINO(R.string.coffee_type_cappuccino, "☕", Color(0xFF6366F1)),
    DESCAFEINADO(R.string.coffee_type_descafeinado, "☕", Color(0xFF10B981)),
    CARAJILLO(R.string.coffee_type_carajillo, "🥃", Color(0xFFB45309)),
    CREMAET(R.string.coffee_type_cremaet, "☕", Color(0xFFFF5722)),

    OTROS(R.string.coffee_type_otros, "🍫", Color(0xFFEF4444));

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

fun Coffee.getRelativeTime(resources: Resources): String {
    return this.timestamp.timeSinceNow().toRelativeString(resources)
}
