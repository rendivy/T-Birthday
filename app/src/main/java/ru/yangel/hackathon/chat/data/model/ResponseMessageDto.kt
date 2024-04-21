package ru.yangel.hackathon.chat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ResponseMessageDto(
    val messageId: String,
    val senderId: String,
    val receiverId: String,
    val content: String,
    val isNotification: Boolean
)
