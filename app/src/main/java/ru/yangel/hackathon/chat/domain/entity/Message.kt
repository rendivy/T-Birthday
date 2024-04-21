package ru.yangel.hackathon.chat.domain.entity

data class Message(
    val messageId: String,
    val senderId: String,
    val receiverId: String,
    val senderName: String,
    val senderAvatarUrl: String,
    val content: String,
    val isNotification: Boolean,
    val isOwnMessage: Boolean
)
