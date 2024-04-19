package ru.yangel.hackathon

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import org.koin.core.component.KoinComponent

class TinkoffApp : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
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

