package ru.yangel.hackathon.calendar.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.yangel.hackathon.calendar.presentation.viewmodel.CalendarViewModel

fun providePresentationModule(): Module = module {

    viewModel { CalendarViewModel(get()) }

}