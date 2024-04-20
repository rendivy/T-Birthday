package ru.yangel.hackathon.profile.data.repository

import ru.yangel.hackathon.profile.api.ProfileApiService

class ProfileRepository(private val profileApi: ProfileApiService) {

    suspend fun getProfile(userId: String) = profileApi.getProfile(userId)

    suspend fun getAllSubscriptions() = profileApi.getAllSubscriptions()

    suspend fun subscribe(userId: String) = profileApi.subscribe(userId)

    suspend fun unsubscribe(userId: String) = profileApi.unsubscribe(userId)

}