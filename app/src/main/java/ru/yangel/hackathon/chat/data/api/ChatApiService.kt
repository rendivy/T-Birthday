package ru.yangel.hackathon.chat.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import ru.yangel.hackathon.chat.data.model.EndpointMessageModel

interface ChatApiService {

    @GET("api/v1/messages/{chatRoomId}")
    suspend fun getMessages(@Path("chatRoomId") chatRoomId: String): List<EndpointMessageModel>

}