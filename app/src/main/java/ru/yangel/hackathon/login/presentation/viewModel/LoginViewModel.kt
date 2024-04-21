package ru.yangel.hackathon.login.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.yangel.hackathon.login.data.LoginRepository
import java.lang.Exception

data class UserStateUi(
    var username: String = "",
    var password: String = ""
)

sealed class UserState {
    data object Initial : UserState()
    data object Loading : UserState()
    data object Error : UserState()
    data object Success : UserState()
}

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    val userUiState = MutableStateFlow(UserStateUi())
    val userState = MutableStateFlow<UserState>(UserState.Initial)

    fun changeUserName(userName: String) {
        userUiState.value = userUiState.value.copy(username = userName)
    }

    fun changePassword(password: String) {
        userUiState.value = userUiState.value.copy(password = password)
    }

    fun login() {
        viewModelScope.launch {
            userState.value = UserState.Loading
            try {
                loginRepository.login(
                    username = userUiState.value.username,
                    password = userUiState.value.password
                )
                userState.value = UserState.Success
            } catch (e: Exception) {
                userState.value = UserState.Error
            }
        }
    }

}