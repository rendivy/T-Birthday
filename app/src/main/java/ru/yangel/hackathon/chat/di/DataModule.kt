package ru.yangel.hackathon.chat.di

import org.koin.dsl.module
import ru.yangel.hackathon.chat.data.repository.ChatMessagesRepository

fun provideChatDataModule() = module {
    single {
        ChatMessagesRepository(get(), get())
    }
}