import java.util.concurrent.TimeUnit
import kotlin.math.abs

data class TimeDuration(
    val days: Long,
    val hours: Long,
    val minutes: Long,
    val seconds: Long
) {

    // ============================================
    // MÉTODOS DE FORMATEO (Lógica de negocio)
    // ============================================

    /**
     * Formato completo: "2 días, 5 horas y 41 minutos"
     */
    fun toReadableString(includeSeconds: Boolean = false): String {
        val parts = mutableListOf<String>()

        if (days > 0) {
            parts.add(if (days == 1L) "1 día" else "$days días")
        }

        if (hours > 0) {
            parts.add(if (hours == 1L) "1 hora" else "$hours horas")
        }

        if (minutes > 0) {
            parts.add(if (minutes == 1L) "1 minuto" else "$minutes minutos")
        }

        if (includeSeconds && seconds > 0 && days == 0L && hours == 0L) {
            parts.add(if (seconds == 1L) "1 segundo" else "$seconds segundos")
        }

        return when {
            parts.isEmpty() -> "Hace un momento"
            parts.size == 1 -> parts[0]
            parts.size == 2 -> "${parts[0]} y ${parts[1]}"
            else -> {
                val last = parts.last()
                val rest = parts.dropLast(1).joinToString(", ")
                "$rest y $last"
            }
        }
    }

    /**
     * Formato corto: "2d 5h 41m"
     */
    fun toShortString(): String {
        val parts = mutableListOf<String>()
        if (days > 0) parts.add("${days}d")
        if (hours > 0) parts.add("${hours}h")
        if (minutes > 0) parts.add("${minutes}m")
        return if (parts.isEmpty()) "Ahora" else parts.joinToString(" ")
    }

    /**
     * Formato relativo: "Hace 2 días"
     */
    fun toRelativeString(): String {
        return when {
            days > 0 -> "Hace ${if (days == 1L) "1 día" else "$days días"}"
            hours > 0 -> "Hace ${if (hours == 1L) "1 hora" else "$hours horas"}"
            minutes > 0 -> "Hace ${if (minutes == 1L) "1 minuto" else "$minutes minutos"}"
            else -> "Hace un momento"
        }
    }

    /**
     * Formato inteligente: Adapta según el tiempo transcurrido
     */
    fun toSmartString(): String {
        return when {
            days == 0L && hours == 0L && minutes == 0L -> "Justo ahora"
            days == 0L && hours == 0L -> "Hace $minutes ${if (minutes == 1L) "minuto" else "minutos"}"
            days == 0L -> toReadableString()
            else -> toReadableString()
        }
    }

    /**
     * Convierte a milisegundos
     */
    fun toMilliseconds(): Long {
        return TimeUnit.DAYS.toMillis(days) +
                TimeUnit.HOURS.toMillis(hours) +
                TimeUnit.MINUTES.toMillis(minutes) +
                TimeUnit.SECONDS.toMillis(seconds)
    }

    // ============================================
    // COMPANION OBJECT - Métodos estáticos (Factory)
    // ============================================

    companion object {
        /**
         * Crea TimeDuration desde un timestamp
         */
        fun fromTimestamp(
            fromTimestamp: Long,
            toTimestamp: Long = System.currentTimeMillis()
        ): TimeDuration {
            val diffMillis = abs(toTimestamp - fromTimestamp)

            val days = TimeUnit.MILLISECONDS.toDays(diffMillis)
            val hours = TimeUnit.MILLISECONDS.toHours(diffMillis) % 24
            val minutes = TimeUnit.MILLISECONDS.toMinutes(diffMillis) % 60
            val seconds = TimeUnit.MILLISECONDS.toSeconds(diffMillis) % 60

            return TimeDuration(days, hours, minutes, seconds)
        }

        /**
         * Crea TimeDuration desde milisegundos
         */
        fun fromMilliseconds(millis: Long): TimeDuration {
            val days = TimeUnit.MILLISECONDS.toDays(millis)
            val hours = TimeUnit.MILLISECONDS.toHours(millis) % 24
            val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60
            val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60

            return TimeDuration(days, hours, minutes, seconds)
        }

        /**
         * TimeDuration cero
         */
        fun zero(): TimeDuration = TimeDuration(0, 0, 0, 0)
    }
}

// ============================================
// EXTENSIONES para Long (timestamp)
// ============================================

/**
 * Calcula el tiempo transcurrido desde este timestamp hasta ahora
 */
fun Long.timeSinceNow(): TimeDuration {
    return TimeDuration.fromTimestamp(this)
}

/**
 * Versión string del tiempo transcurrido
 */
fun Long.timeSinceNowAsString(): String {
    return this.timeSinceNow().toReadableString()
}

/**
 * Versión inteligente
 */
fun Long.smartTimeSince(): String {
    return this.timeSinceNow().toSmartString()
}