package ru.yangel.hackathon.follows.di

import android.content.Context
import android.util.Log
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.yangel.hackathon.ai.viewModel.AiViewModel
import ru.yangel.hackathon.auth.data.api.LoginApi
import ru.yangel.hackathon.calendar.data.db.SubscriptionsDatabase
import ru.yangel.hackathon.follows.data.api.SearchUsersApiService
import ru.yangel.hackathon.follows.data.repository.FollowsRepository
import ru.yangel.hackathon.follows.presentation.viewmodel.SearchViewModel
import ru.yangel.hackathon.login.data.LoginRepository
import ru.yangel.hackathon.login.data.TokenLocalStorage
import ru.yangel.hackathon.login.presentation.viewModel.LoginViewModel
import ru.yangel.hackathon.profile.api.ProfileApiService
import ru.yangel.hackathon.profile.data.repository.ProfileRepository
import ru.yangel.hackathon.profile.presentation.viewmodel.ProfileViewModel

fun provideFollowsModule() = module {
    viewModel { SearchViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { AiViewModel(get()) }
    single { provideSearchUsersApiService(get()) }
    single { provideProfileApiService(get()) }
    factory { provideRepository(get()) }
    factory { provideProfileRepository(get(), get()) }
    factory { provideLoginRepository(get(), get()) }
    single { provideTokenStorage(androidContext().applicationContext) }
}


fun provideSearchUsersApiService(retrofit: Retrofit): SearchUsersApiService =
    retrofit.create(SearchUsersApiService::class.java)

fun provideProfileApiService(retrofit: Retrofit): ProfileApiService =
    retrofit.create(ProfileApiService::class.java)

fun provideRepository(searchUsersApiService: SearchUsersApiService): FollowsRepository {
    return FollowsRepository(searchUsersApiService)
}

fun provideProfileRepository(
    profileApiService: ProfileApiService,
    database: SubscriptionsDatabase
): ProfileRepository {
    return ProfileRepository(profileApiService, database)
}

fun provideTokenStorage(context: Context): TokenLocalStorage {
    return TokenLocalStorage(context)
}

fun provideLoginRepository(
    tokenLocalStorage: TokenLocalStorage,
    loginApi: LoginApi
): LoginRepository {
    return LoginRepository(tokenLocalStorage, loginApi)
}
