package ru.yangel.hackathon.follows.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.yangel.hackathon.follows.data.api.SearchUsersApiService
import ru.yangel.hackathon.follows.data.repository.FollowsRepository
import ru.yangel.hackathon.follows.presentation.viewmodel.SearchViewModel

fun provideFollowsModule() = module {
    viewModel { SearchViewModel(get()) }
    single { provideSearchUsersApiService(get()) }
    single { provideRepository(get()) }
}


fun provideSearchUsersApiService(retrofit: Retrofit): SearchUsersApiService =
    retrofit.create(SearchUsersApiService::class.java)


fun provideRepository(searchUsersApiService: SearchUsersApiService): FollowsRepository {
    return FollowsRepository(searchUsersApiService)
}

