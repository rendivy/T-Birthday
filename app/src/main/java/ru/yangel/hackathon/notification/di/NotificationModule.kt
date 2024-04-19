package ru.yangel.hackathon.notification.di

import androidx.work.DelegatingWorkerFactory
import androidx.work.WorkerFactory
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module
import ru.yangel.hackathon.notification.workmanager.NotificationWorker


val workerModule = module {
    worker { NotificationWorker(androidContext(), get()) }
}

val appModule = module {
    single { androidContext() }
}