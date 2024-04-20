package ru.yangel.hackathon.calendar.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "personalSubscriptions")
data class PersonalSubscriptionDto(
    @PrimaryKey
    val id: String,
    val username: String,
    val email: String,
    val fullName: String,
    val photoUrl: String,
    val birthDate: LocalDate
)
