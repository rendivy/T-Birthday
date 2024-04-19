package ru.yangel.hackathon.notification.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.yangel.hackathon.notification.database.converter.Converters
import ru.yangel.hackathon.notification.database.dao.SubscribedUserDAO
import ru.yangel.hackathon.notification.database.entity.SubscribedUser

@Database(entities = [SubscribedUser::class], version = 1)
@TypeConverters(Converters::class)
abstract class DateDatabase : RoomDatabase() {
    abstract fun subscribedUserDao(): SubscribedUserDAO
}