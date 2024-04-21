package ru.yangel.hackathon.ai.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.yangel.hackathon.profile.data.repository.ProfileRepository

class AiViewModel(private val profileRepository: ProfileRepository) : ViewModel() {

    val userUi = MutableStateFlow("")
    val userState = MutableStateFlow<AiState>(AiState.Initial)

    fun getMessage() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userState.value = AiState.Loading
                val aiAnswer = profileRepository.getAiAnswers(userUi.value)
                userState.value = AiState.Content(aiAnswer)
            }
            catch (e: Exception) {
                userState.value = AiState.Error
            }
        }
    }

    fun updateUi(message: String) {
        userUi.value = message
    }
}


sealed class AiState {
    data object Initial : AiState()
    data object Loading : AiState()
    data class Content(val message: String) : AiState()
    data object Error : AiState()
}

