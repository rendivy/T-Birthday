package ru.yangel.hackathon.chat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RequestMessageDto(
    val recipientId: String,
    val content: String
)
