package ru.yangel.hackathon.chat.di

import org.koin.dsl.module
import ru.yangel.hackathon.chat.data.api.ChatApiService
import ru.yangel.hackathon.di.provideRetrofit

fun provideChatNetworkModule() = module {
    single {
        provideRetrofit(get(), get()).create(ChatApiService::class.java)
    }
}