package ru.yangel.hackathon.profile.data.repository

import ru.yangel.hackathon.calendar.data.db.SubscriptionsDatabase
import ru.yangel.hackathon.calendar.data.entity.PersonalSubscriptionDto
import ru.yangel.hackathon.profile.api.ProfileApiService
import ru.yangel.hackathon.profile.data.model.UserDetailsResponse
import java.time.LocalDate

class ProfileRepository(
    private val profileApi: ProfileApiService,
    private val database: SubscriptionsDatabase
) {

    suspend fun getProfile(userId: String) = profileApi.getProfile(userId)

    suspend fun getAllSubscriptions() = profileApi.getAllSubscriptions()

    suspend fun subscribe(userId: String) {
        profileApi.subscribe(userId)
        val user = UserDetailsMapper.map(profileApi.getProfile(userId))
        database.subscriptionsDao().upsertPersonalSubscription(user)
    }

    suspend fun unsubscribe(userId: String) {
        profileApi.unsubscribe(userId)
        val user = database.subscriptionsDao().getUserById(userId)
        database.subscriptionsDao().deletePersonalSubscription(user)
    }
}


object UserDetailsMapper {

    fun map(user: UserDetailsResponse): PersonalSubscriptionDto {
        return PersonalSubscriptionDto(
            id = user.id,
            username = user.username,
            email = user.email,
            fullName = user.fullName,
            photoUrl = user.photoUrl,
            birthDate = LocalDate.parse(user.birthDate)
        )
    }
}