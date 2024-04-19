package ru.yangel.hackathon.notification.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.yangel.hackathon.notification.database.dao.SubscribedUserDAO
import ru.yangel.hackathon.notification.database.entity.SubscribedUser

@Database(entities = [SubscribedUser::class], version = 1)
abstract class DateDatabase : RoomDatabase() {
    abstract fun subscribedUserDao(): SubscribedUserDAO
}