import android.content.res.Resources
import java.util.concurrent.TimeUnit
import kotlin.math.abs
import com.calleserpis.coffeetracker.R

data class TimeDuration(
    val days: Long,
    val hours: Long,
    val minutes: Long,
    val seconds: Long
) {

    /**
     * Formato completo: "2 días, 5 horas y 41 minutos"
     */
    fun toReadableString(resources: Resources, includeSeconds: Boolean = false): String {
        val parts = mutableListOf<String>()

        if (days > 0) {
            parts.add(resources.getQuantityString(R.plurals.time_days, days.toInt(), days))
        }

        if (hours > 0) {
            parts.add(resources.getQuantityString(R.plurals.time_hours, hours.toInt(), hours))
        }

        if (minutes > 0) {
            parts.add(resources.getQuantityString(R.plurals.time_minutes, minutes.toInt(), minutes))
        }

        if (includeSeconds && seconds > 0 && days == 0L && hours == 0L) {
            parts.add(resources.getQuantityString(R.plurals.time_seconds, seconds.toInt(), seconds))
        }

        return when {
            parts.isEmpty() -> resources.getString(R.string.time_now)
            parts.size == 1 -> parts[0]
            parts.size == 2 -> "${parts[0]} ${resources.getString(R.string.time_connector_and)} ${parts[1]}"
            else -> {
                val last = parts.last()
                val rest = parts.dropLast(1).joinToString(", ")
                "$rest ${resources.getString(R.string.time_connector_and)} $last"
            }
        }
    }

    /**
     * Formato corto: "2d 5h 41m"
     */
    fun toShortString(resources: Resources): String {
        val parts = mutableListOf<String>()
        if (days > 0) parts.add("${days}d")
        if (hours > 0) parts.add("${hours}h")
        if (minutes > 0) parts.add("${minutes}m")
        return if (parts.isEmpty()) resources.getString(R.string.time_short_now) else parts.joinToString(" ")
    }

    /**
     * Formato relativo: "Hace 2 días"
     */
    fun toRelativeString(resources: Resources): String {
        val timeText = when {
            days > 0 -> resources.getQuantityString(R.plurals.time_days, days.toInt(), days)
            hours > 0 -> resources.getQuantityString(R.plurals.time_hours, hours.toInt(), hours)
            minutes > 0 -> resources.getQuantityString(R.plurals.time_minutes, minutes.toInt(), minutes)
            else -> return resources.getString(R.string.time_now)
        }
        return resources.getString(R.string.time_ago, timeText)
    }

    /**
     * Formato inteligente: Adapta según el tiempo transcurrido
     */
    fun toSmartString(resources: Resources): String {
        return when {
            days == 0L && hours == 0L && minutes == 0L -> resources.getString(R.string.time_just_now)
            days == 0L && hours == 0L -> {
                val minuteText = resources.getQuantityString(R.plurals.time_minutes, minutes.toInt(), minutes)
                resources.getString(R.string.time_ago, minuteText)
            }
            else -> toReadableString(resources)
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

    companion object {
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

        fun fromMilliseconds(millis: Long): TimeDuration {
            val days = TimeUnit.MILLISECONDS.toDays(millis)
            val hours = TimeUnit.MILLISECONDS.toHours(millis) % 24
            val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60
            val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60

            return TimeDuration(days, hours, minutes, seconds)
        }

        fun zero(): TimeDuration = TimeDuration(0, 0, 0, 0)
    }
}

fun Long.timeSinceNow(): TimeDuration {
    return TimeDuration.fromTimestamp(this)
}

// Ya no necesitas estas extensiones porque ahora pasas Resources como parámetro