package ru.yangel.hackathon.splash.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.yangel.hackathon.splash.presentation.viewmodel.SplashViewModel

fun provideSplashPresentationModule(): Module = module {

    viewModel { SplashViewModel(get()) }

}