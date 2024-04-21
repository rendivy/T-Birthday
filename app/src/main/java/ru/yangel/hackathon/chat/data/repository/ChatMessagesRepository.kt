package ru.yangel.hackathon.chat.data.repository

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.filter
import ru.yangel.hackathon.chat.data.api.ChatApiService
import ru.yangel.hackathon.chat.data.model.EndpointMessageModel
import ru.yangel.hackathon.chat.domain.entity.Message
import ru.yangel.hackathon.profile.api.ProfileApiService
import java.util.UUID


class ChatMessagesRepository(
    private val chatApiService: ChatApiService,
    private val profileApiService: ProfileApiService
) {

    private val messageSendFlow = MutableSharedFlow<Pair<String, String>>()

    fun getMessagesFlow(chatRoomId: String) = channelFlow {
        val messages = mutableListOf<Message>()
        val ownProfile = profileApiService.getOwnProfile()
        val remoteMessages = chatApiService.getMessages(chatRoomId)
        messages.addAll(remoteMessages.toMessages(ownProfile.id))
        send(messages)

        messageSendFlow.filter { it.first == chatRoomId }.collect {
            send(
                listOf(Message(
                    messageId = UUID.randomUUID().toString(),
                    senderId = ownProfile.id,
                    receiverId = chatRoomId,
                    senderName = ownProfile.fullName,
                    senderAvatarUrl = ownProfile.photoUrl,
                    content = it.second,
                    isNotification = false,
                    isOwnMessage = true
                ))
            )
        }
    }

    suspend fun sendMessage(chatRoomId: String, message: String) {
        messageSendFlow.emit(chatRoomId to message)
    }

}

private fun List<EndpointMessageModel>.toMessages(userId: String): List<Message> =
    this.sortedBy { it.messageId }.map {
        Message(
            messageId = it.messageId,
            senderId = it.senderId,
            receiverId = it.chatRoomId,
            senderName = it.fullName,
            senderAvatarUrl = it.avatarUrl,
            content = it.content,
            isNotification = it.isNotification,
            isOwnMessage = userId == it.senderId
        )
    }

