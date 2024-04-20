package ru.yangel.hackathon.follows.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.yangel.hackathon.follows.presentation.viewmodel.SearchViewModel

val followsModule = module {
    viewModel {SearchViewModel() }
}