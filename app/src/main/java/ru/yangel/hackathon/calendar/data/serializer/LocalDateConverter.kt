package ru.yangel.hackathon.calendar.data.serializer

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val DATETIME_PATTERN = "yyyy-MM-dd"
private val dateTimeFormatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN)

object LocalDateConverter {

    @TypeConverter
    fun toDate(dateString: String?): LocalDate? {
        return if (dateString == null) {
            null
        } else {
            LocalDate.parse(dateString, dateTimeFormatter)
        }
    }

    @TypeConverter
    fun toDateString(date: LocalDate?): String? {
        return date?.toString()
    }

}