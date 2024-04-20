package ru.yangel.hackathon.follows.di

import android.provider.ContactsContract.Profile
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.yangel.hackathon.follows.data.api.SearchUsersApiService
import ru.yangel.hackathon.follows.data.repository.FollowsRepository
import ru.yangel.hackathon.follows.presentation.viewmodel.SearchViewModel
import ru.yangel.hackathon.profile.api.ProfileApiService
import ru.yangel.hackathon.profile.data.repository.ProfileRepository
import ru.yangel.hackathon.profile.presentation.viewmodel.ProfileViewModel

fun provideFollowsModule() = module {
    viewModel { SearchViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    single { provideSearchUsersApiService(get()) }
    single { provideProfileApiService(get()) }
    factory { provideRepository(get()) }
    factory { provideProfileRepository(get()) }
}


fun provideSearchUsersApiService(retrofit: Retrofit): SearchUsersApiService =
    retrofit.create(SearchUsersApiService::class.java)

fun provideProfileApiService(retrofit: Retrofit): ProfileApiService =
    retrofit.create(ProfileApiService::class.java)

fun provideRepository(searchUsersApiService: SearchUsersApiService): FollowsRepository {
    return FollowsRepository(searchUsersApiService)
}

fun provideProfileRepository(profileApiService: ProfileApiService): ProfileRepository {
    return ProfileRepository(profileApiService)
}

