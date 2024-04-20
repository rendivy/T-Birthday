package ru.yangel.hackathon

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import ru.yangel.hackathon.follows.di.followsModule

class TinkoffApp : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(followsModule)
        }
        createNotificationChannel()
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

