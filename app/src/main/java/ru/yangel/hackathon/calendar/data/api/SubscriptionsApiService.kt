package ru.yangel.hackathon.calendar.data.api

import retrofit2.http.GET
import ru.yangel.hackathon.calendar.data.entity.PersonalSubscriptionDto

interface SubscriptionsApiService {

    @GET("/api/v1/subscriptions/person/all")
    suspend fun getAllPersonalSubscriptions(): List<PersonalSubscriptionDto>

}