package ru.yangel.hackathon.calendar.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.yangel.hackathon.calendar.data.dao.SubscriptionsDao
import ru.yangel.hackathon.calendar.data.entity.PersonalSubscriptionDto
import ru.yangel.hackathon.calendar.data.serializer.LocalDateConverter

@Database(
    entities = [
        PersonalSubscriptionDto::class
    ],
    version = 1
)
@TypeConverters(LocalDateConverter::class)
abstract class SubscriptionsDatabase : RoomDatabase() {
    abstract fun subscriptionsDao(): SubscriptionsDao
}