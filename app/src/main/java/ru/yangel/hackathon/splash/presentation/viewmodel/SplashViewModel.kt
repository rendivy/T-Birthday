package ru.yangel.hackathon.splash.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.yangel.hackathon.calendar.data.repository.SubscriptionsRepository

class SplashViewModel(
    private val subscriptionsRepository: SubscriptionsRepository
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("SplashViewModel", "error: could not retrieve and save personal subscriptions \n${throwable.stackTraceToString()}")
    }

    init {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            subscriptionsRepository.getAndSaveRemotePersonalSubscriptions()
        }
    }

}