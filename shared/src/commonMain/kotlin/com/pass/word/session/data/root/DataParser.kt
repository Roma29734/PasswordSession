package com.pass.word.session.data.root

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime

fun getDaysOrMonthsOrYearsDifference(inputDate: String) {
    val currentDate: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
    val targetDate = parseStringToLocalDateTime(inputDate)
    println("currentDate - $currentDate")
    println("custom targetDate - $targetDate")
}

fun parseStringToLocalDateTime(inputDate: String): LocalDateTime? {
    return try {
        val parts = inputDate.split(".")
        val year = parts[2].toInt()
        val month = parts[1].toInt()
        val day = parts[0].toInt()

        val localDate = LocalDate(year, month, day)
        localDate.atStartOfDayIn(TimeZone.currentSystemDefault()).toLocalDateTime(TimeZone.UTC)
    } catch (e: Exception) {
        null
    }
}