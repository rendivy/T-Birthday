package ru.yangel.hackathon.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.yangel.hackathon.profile.data.model.UserDetailsResponse
import ru.yangel.hackathon.profile.data.repository.ProfileRepository


sealed class ProfileState {
    data object Initial : ProfileState()
    data object Loading : ProfileState()
    data class Success(val profile: UserDetailsResponse) : ProfileState()
    data class Error(val error: Throwable) : ProfileState()
}

class ProfileViewModel(private val profileRepository: ProfileRepository) : ViewModel() {

    val profileState = MutableStateFlow<ProfileState>(ProfileState.Initial)
    val isUserAlreadySubscribed = MutableStateFlow(false)

    fun getProfile(userId: String) {
        viewModelScope.launch {
            profileState.value = ProfileState.Loading
            getAllSubscriptions(userId)
            try {
                val profile = profileRepository.getProfile(userId)
                profileState.value = ProfileState.Success(profile)
            } catch (e: Exception) {
                profileState.value = ProfileState.Error(e)
            }
        }
    }

    fun subscribe(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                profileRepository.subscribe(userId)
                isUserAlreadySubscribed.value = true

            } catch (e: Exception) {
                profileState.value = ProfileState.Error(e)
            }
        }
    }

    fun unsubscribe(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                profileRepository.unsubscribe(userId)
                isUserAlreadySubscribed.value = false
            } catch (e: Exception) {
                profileState.value = ProfileState.Error(e)
            }
        }
    }

    fun getAllSubscriptions(userId: String) {
        viewModelScope.launch {
            try {
                val allSubscriptions = profileRepository.getAllSubscriptions()
                isUserAlreadySubscribed.value = allSubscriptions.any { it.id == userId }
            } catch (e: Exception) {
                profileState.value = ProfileState.Error(e)
            }
        }
    }

    fun retryState() {
        profileState.value = ProfileState.Initial
    }
}