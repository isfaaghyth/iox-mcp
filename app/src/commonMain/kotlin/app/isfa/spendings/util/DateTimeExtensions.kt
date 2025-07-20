package app.isfa.spendings.util

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
fun Long.formatReadableDateTime(): String {
    val instant = Instant.fromEpochMilliseconds(this)

    val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    val dayOfWeek = getDayOfWeekShort(localDateTime.dayOfWeek)
    val day = localDateTime.day.toString().padStart(2, ' ')
    val month = getMonthShort(localDateTime.month)
    val year = localDateTime.year
    val yearStr = if (currentDateTime.year != year) year else ""

    // Format date as "Sun, 30 Mar"
    val date = "$dayOfWeek, $day $month $yearStr".trim()

    // Format time as "10:23 AM"
    val time = localDateTime.toReadableTime()

    return if (currentDateTime.date == localDateTime.date) time else date
}

fun getDayOfWeekShort(dayOfWeek: DayOfWeek): String {
    return dayOfWeek.name
        .take(3)
        .lowercase()
        .replaceFirstChar { it.uppercase() }
}

fun getMonthShort(month: Month): String {
    return month.name
        .take(3)
        .lowercase()
        .replaceFirstChar { it.uppercase() }
}

fun LocalDateTime.toReadableTime(): String {
    val isAM = hour < 12
    val timeStatus = if (isAM) "AM" else "PM"

    return buildString {
        append(hour.toString().padStart(2, '0'))
        append(":")
        append(minute.toString().padStart(2, '0'))
        append(" $timeStatus")
    }
}