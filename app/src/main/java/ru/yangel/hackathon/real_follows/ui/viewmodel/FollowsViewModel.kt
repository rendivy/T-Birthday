package ru.yangel.hackathon.real_follows.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.yangel.hackathon.follows.data.repository.FollowsRepository
import ru.yangel.hackathon.follows.presentation.viewmodel.AffiliatesState
import ru.yangel.hackathon.follows.presentation.viewmodel.TeamState
import ru.yangel.hackathon.follows.presentation.viewmodel.UsersState

class FollowsViewModel(
    private val followsRepository: FollowsRepository
) : ViewModel() {

    val state = MutableStateFlow("")

    val usersState = MutableStateFlow<UsersState>(UsersState.Initial)
    val teamState = MutableStateFlow<TeamState>(TeamState.Initial)
    val affiliatesState = MutableStateFlow<AffiliatesState>(AffiliatesState.Initial)

    fun searchFollowedUsersByName() {
        usersState.value = UsersState.Loading
        viewModelScope.launch {
            try {
                val users = followsRepository.searchFollowedUsersByName()
                usersState.value = UsersState.Success(users)
            } catch (e: Exception) {
                usersState.value = UsersState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun searchFollowedCommand() {
        teamState.value = TeamState.Loading
        viewModelScope.launch {
            try {
                val users = followsRepository.searchFollowedUsersByCommand()
                teamState.value = TeamState.Success(users)
            } catch (e: Exception) {
                teamState.value = TeamState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun searchFollowedAffiliate() {
        affiliatesState.value = AffiliatesState.Loading
        viewModelScope.launch {
            try {
                val users = followsRepository.searchFollowedUsersByAffiliate()
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