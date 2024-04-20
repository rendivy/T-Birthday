package ru.yangel.hackathon.follows.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.yangel.hackathon.follows.data.model.UserSearchResponse
import ru.yangel.hackathon.follows.data.repository.FollowsRepository


sealed class UsersState {
    data object Initial : UsersState()
    data object Loading : UsersState()
    data class Success(val users: List<UserSearchResponse>) : UsersState()
    data class Error(val message: String) : UsersState()
}


class SearchViewModel(private val followsRepository: FollowsRepository) : ViewModel() {

    val state = MutableStateFlow("")

    val usersState = MutableStateFlow<UsersState>(UsersState.Initial)

    fun searchUsersByName(username: String) {
        usersState.value = UsersState.Loading
        viewModelScope.launch {
            try {
                val users = followsRepository.searchUsersByName(username)
                usersState.value = UsersState.Success(users)
            } catch (e: Exception) {
                usersState.value = UsersState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun onStateChange(newValue: String) {
        state.value = newValue
    }
}