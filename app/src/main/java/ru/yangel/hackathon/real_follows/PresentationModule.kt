package ru.yangel.hackathon.real_follows

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.yangel.hackathon.real_follows.ui.viewmodel.FollowsViewModel

fun provideRealFollowsPresentationModule(): Module = module {

    viewModel { FollowsViewModel(get()) }

}