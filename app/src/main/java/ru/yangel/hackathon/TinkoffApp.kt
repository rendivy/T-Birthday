package ru.yangel.hackathon

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import ru.yangel.hackathon.follows.di.followsModule
import org.koin.core.context.startKoin
import ru.yangel.hackathon.auth.di.provideAuthNetworkModule
import ru.yangel.hackathon.wishlist.item.di.provideWishlistItemDomainModule
import ru.yangel.hackathon.wishlist.item.di.provideWishlistItemPresentationModule
import ru.yangel.hackathon.wishlist.item.di.provideWishlistItemNetworkModule

class TinkoffApp : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        startKoin {
        }
        createNotificationChannel()
        startKoin {
            androidContext(this@TinkoffApp)
            modules(followsModule)
            modules(
                provideWishlistItemPresentationModule(),
                provideAuthNetworkModule(),
                provideWishlistItemNetworkModule(),
                provideWishlistItemDomainModule()
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

