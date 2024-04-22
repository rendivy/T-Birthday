package ru.yangel.hackathon.calendar.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.yangel.hackathon.calendar.data.entity.PersonalSubscriptionDto

@Dao
interface SubscriptionsDao {

    @Upsert
    suspend fun upsertAllPersonalSubscriptions(subscriptions: List<PersonalSubscriptionDto>)

    @Query("DELETE FROM personalSubscriptions")
    suspend fun deleteAllPersonalSubscriptions()

    @Upsert
    suspend fun upsertPersonalSubscription(subscription: PersonalSubscriptionDto)

    @Delete
    suspend fun deletePersonalSubscription(subscription: PersonalSubscriptionDto)

    @Query("SELECT * FROM personalSubscriptions")
    fun getAllPersonalSubscriptions(): Flow<List<PersonalSubscriptionDto>>

    @Query("SELECT * FROM personalSubscriptions WHERE id=:userId")
    fun getUserById(userId: String): PersonalSubscriptionDto
}