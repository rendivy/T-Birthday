package ru.yangel.hackathon.calendar.data.repository

import kotlinx.coroutines.flow.Flow
import ru.yangel.hackathon.calendar.data.api.SubscriptionsApiService
import ru.yangel.hackathon.calendar.data.dao.SubscriptionsDao
import ru.yangel.hackathon.calendar.data.entity.PersonalSubscriptionDto

class SubscriptionsRepository(
    private val subscriptionsDao: SubscriptionsDao,
    private val subscriptionsApiService: SubscriptionsApiService
) {

    suspend fun getAndSaveRemotePersonalSubscriptions() {
        val remotePersonalSubscriptions = subscriptionsApiService.getAllPersonalSubscriptions()
        subscriptionsDao.upsertAllPersonalSubscriptions(remotePersonalSubscriptions)
    }

    fun getLocalPersonalSubscriptions(): Flow<List<PersonalSubscriptionDto>> {
        return subscriptionsDao.getAllPersonalSubscriptions()
    }

}