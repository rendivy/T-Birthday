package ru.yangel.hackathon.notification.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SubscribedUser(
    @PrimaryKey(autoGenerate = true)
    val userId: Long,
    val dateOfBirth: String
)