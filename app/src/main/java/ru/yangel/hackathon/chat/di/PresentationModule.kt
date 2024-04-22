package ru.yangel.hackathon.chat.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.yangel.hackathon.chat.presentation.ChatViewModel

fun provideChatPresentationModule() = module {
    viewModel { parametersHolder ->
        ChatViewModel(parametersHolder.get(), get())
    }
}