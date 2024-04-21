package ru.yangel.hackathon.chat.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.yangel.hackathon.chat.data.repository.ChatMessagesRepository
import ru.yangel.hackathon.chat.domain.entity.Message
import ru.yangel.hackathon.chat.presentation.state.ChatState

class ChatViewModel(
    private val chatId: String, private val chatMessagesRepository: ChatMessagesRepository
) : ViewModel() {

    private val _screenState = mutableStateOf(ChatState.Loading)
    val screenState: State<ChatState>
        get() = _screenState

    private val _messagesList = mutableStateListOf<Message>()
    val messagesList: SnapshotStateList<Message>
        get() = _messagesList

    val message = mutableStateOf("")

    init {
        reload()
    }

    fun reload() {
        _screenState.value = ChatState.Loading
        _messagesList.clear()
        chatMessagesRepository.getMessagesFlow(chatId).onEach {
            if (_screenState.value != ChatState.Content)
                _screenState.value = ChatState.Content
            _messagesList.addAll(it)
        }.catch {
            _screenState.value = ChatState.Error
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }

    fun sendMessage() {
        viewModelScope.launch {
            chatMessagesRepository.sendMessage(chatId, message.value)
            message.value = ""
        }
    }

}