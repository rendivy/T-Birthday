package ru.yangel.hackathon.calendar.di

import android.content.Context
import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.yangel.hackathon.calendar.data.api.SubscriptionsApiService
import ru.yangel.hackathon.calendar.data.dao.SubscriptionsDao
import ru.yangel.hackathon.calendar.data.db.SubscriptionsDatabase
import ru.yangel.hackathon.calendar.data.repository.SubscriptionsRepository

private fun provideSubscriptionsDatabase(context: Context): SubscriptionsDatabase =
    Room.databaseBuilder(
        context,
        SubscriptionsDatabase::class.java,
        "subscriptions_db"
    ).build()

private fun provideSubscriptionsDao(
    subscriptionsDatabase: SubscriptionsDatabase
): SubscriptionsDao = subscriptionsDatabase.subscriptionsDao()

private fun provideSubscriptionsApiService(retrofit: Retrofit): SubscriptionsApiService =
    retrofit.create(SubscriptionsApiService::class.java)

private fun provideSubscriptionsRepository(
    subscriptionsDao: SubscriptionsDao,
    subscriptionsApiService: SubscriptionsApiService
): SubscriptionsRepository =
    SubscriptionsRepository(subscriptionsDao, subscriptionsApiService)


fun provideCalendarDataModule(): Module = module {

    single { provideSubscriptionsDatabase(androidApplication().applicationContext) }

    single { provideSubscriptionsDao(get()) }

    single { provideSubscriptionsApiService(get()) }

    single { provideSubscriptionsRepository(get(), get()) }

}