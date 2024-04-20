package ru.yangel.hackathon.calendar.data.serializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val DATETIME_PATTERN = "yyyy-MM-dd"
private val dateTimeFormatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN)

object LocalDateDeserializer : JsonDeserializer<LocalDate> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDate {
        if (json != null) {
            return LocalDate.parse(json.asString, dateTimeFormatter)
        }
        throw IllegalArgumentException("Cannot convert null to LocalDateTime")
    }
}