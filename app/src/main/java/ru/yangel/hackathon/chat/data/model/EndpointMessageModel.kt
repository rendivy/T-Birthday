package ru.yangel.hackathon.chat.data.model

import com.google.gson.annotations.SerializedName

data class EndpointMessageModel(
    @SerializedName("message_id") val messageId: String,
    @SerializedName("chat_room_id") val chatRoomId: String,
    @SerializedName("sender_id") val senderId: String,
    @SerializedName("content") val content: String
)
