package ru.yangel.hackathon.notification.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.yangel.hackathon.notification.database.entity.SubscribedUser

@Dao
interface SubscribedUserDAO {

    @Query("SELECT * FROM subscribedUser")
    fun getAll(): List<SubscribedUser>

    @Insert
    fun insertSubscribedUser(user: SubscribedUser)

}