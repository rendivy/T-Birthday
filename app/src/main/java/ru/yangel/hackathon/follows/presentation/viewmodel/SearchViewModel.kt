package ru.yangel.hackathon.follows.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.yangel.hackathon.follows.data.model.AffiliateResponse
import ru.yangel.hackathon.follows.data.model.CommandResponse
import ru.yangel.hackathon.follows.data.model.UserSearchResponse
import ru.yangel.hackathon.follows.data.repository.FollowsRepository


sealed class UsersState {
    data object Initial : UsersState()
    data object Loading : UsersState()
    data class Success(val users: List<UserSearchResponse>) : UsersState()
    data class Error(val message: String) : UsersState()
}

sealed class TeamState {
    data object Initial : TeamState()
    data object Loading : TeamState()
    data class Success(val users: List<CommandResponse>) : TeamState()
    data class Error(val message: String) : TeamState()
}

sealed class AffiliatesState {
    data object Initial : AffiliatesState()
    data object Loading : AffiliatesState()
    data class Success(val users: List<AffiliateResponse>) : AffiliatesState()
    data class Error(val message: String) : AffiliatesState()
}



class SearchViewModel(private val followsRepository: FollowsRepository) : ViewModel() {

    val state = MutableStateFlow("")

    val usersState = MutableStateFlow<UsersState>(UsersState.Initial)
    val teamState = MutableStateFlow<TeamState>(TeamState.Initial)
    val affiliatesState = MutableStateFlow<AffiliatesState>(AffiliatesState.Initial)


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

    fun searchCommand(command: String) {
        teamState.value = TeamState.Loading
        viewModelScope.launch {
            try {
                val users = followsRepository.searchUsersByCommand(command)
                teamState.value = TeamState.Success(users)
            } catch (e: Exception) {
                teamState.value = TeamState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun searchAffiliate(affiliate: String) {
        affiliatesState.value = AffiliatesState.Loading
        viewModelScope.launch {
            try {
                val users = followsRepository.searchUsersByAffiliate(affiliate)
                affiliatesState.value = AffiliatesState.Success(users)
            } catch (e: Exception) {
                affiliatesState.value = AffiliatesState.Error(e.message ?: "Unknown error")
            }
        }
    }



    fun resetUsersState() {
        usersState.value = UsersState.Initial
    }

    fun resetTeamState() {
        teamState.value = TeamState.Initial
    }

    fun resetAffiliatesState() {
        affiliatesState.value = AffiliatesState.Initial
    }

    fun onStateChange(newValue: String) {
        state.value = newValue
    }
}