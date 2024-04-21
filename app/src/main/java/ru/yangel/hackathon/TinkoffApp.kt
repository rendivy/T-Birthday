package ru.yangel.hackathon

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import ru.yangel.hackathon.auth.di.provideAuthNetworkModule
import ru.yangel.hackathon.calendar.di.provideCalendarDataModule
import ru.yangel.hackathon.calendar.di.providePresentationModule
import ru.yangel.hackathon.di.provideNetworkModule
import ru.yangel.hackathon.follows.di.provideFollowsModule
import ru.yangel.hackathon.real_follows.provideRealFollowsPresentationModule
import ru.yangel.hackathon.splash.di.provideSplashPresentationModule
import ru.yangel.hackathon.wishlist.item.di.provideWishlistItemDomainModule
import ru.yangel.hackathon.wishlist.item.di.provideWishlistItemNetworkModule
import ru.yangel.hackathon.wishlist.item.di.provideWishlistItemPresentationModule

class TinkoffApp : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startKoin {
            androidContext(this@TinkoffApp)
            modules(
                provideWishlistItemPresentationModule(),
                provideFollowsModule(),
                provideNetworkModule(),
                provideAuthNetworkModule(),
                provideWishlistItemNetworkModule(),
                provideWishlistItemDomainModule(),
                provideCalendarDataModule(),
                provideSplashPresentationModule(),
                providePresentationModule(),
                provideRealFollowsPresentationModule()
            )
        }
    }


    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            "download_channel",
            "File download",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }


}

