package ru.yangel.hackathon.chat.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.stomp.conversions.kxserialization.json.withJsonConversions
import org.hildan.krossbow.stomp.headers.StompSendHeaders
import org.hildan.krossbow.stomp.headers.StompSubscribeHeaders
import org.hildan.krossbow.websocket.WebSocketClient
import org.hildan.krossbow.websocket.builtin.builtIn
import ru.yangel.hackathon.auth.data.api.LoginApi
import ru.yangel.hackathon.chat.data.api.ChatApiService
import ru.yangel.hackathon.chat.data.model.EndpointMessageModel
import ru.yangel.hackathon.chat.data.model.RequestMessageDto
import ru.yangel.hackathon.chat.data.model.ResponseMessageDto
import ru.yangel.hackathon.chat.domain.entity.Message

class ChatMessagesRepository(
    private val chatApiService: ChatApiService, private val loginApi: LoginApi
) {

    private val messageSendFlow = MutableSharedFlow<Pair<String, String>>()

    fun getMessagesFlow(chatRoomId: String) = channelFlow {
        val messages = mutableMapOf<String, Message>()
        val remoteMessages = chatApiService.getMessages(chatRoomId)
        messages.putAll(remoteMessages.toMessages())
        send(messages.values.sortedBy { it.messageId })
        val tokens = loginApi.login()

        val client = StompClient(WebSocketClient.builtIn())
        val session = client.connect(
            url = "ws://158.160.75.21:8080/ws",
            customStompConnectHeaders = mapOf("Authorization" to "Bearer ${tokens.accessToken}")
        ).withJsonConversions()

        val messagesFlow: Flow<ResponseMessageDto> = session.subscribe(
            StompSubscribeHeaders(
                destination = "/topic/$chatRoomId/messages",
                customHeaders = mapOf("Authorization" to "Bearer ${tokens.accessToken}")
            ), ResponseMessageDto.serializer()
        )

        launch {
            messagesFlow.collect { messageDto ->
                messages[messageDto.messageId] = messageDto.toMessage()
                send(messages.values.sortedBy { it.messageId })
            }
        }

        launch {
            messageSendFlow.filter { it.first == chatRoomId }.collect { (chatRoomId, message) ->
                session.convertAndSend(
                    StompSendHeaders(
                        destination = "/app/chat",
                        customHeaders = mapOf("Authorization" to "Bearer ${tokens.accessToken}")
                    ), RequestMessageDto(chatRoomId, message), RequestMessageDto.serializer()
                )
            }
        }
    }

    suspend fun sendMessage(chatRoomId: String, message: String) {
        messageSendFlow.emit(chatRoomId to message)
    }

}

private fun ResponseMessageDto.toMessage(): Message {
    TODO("Not yet implemented")
}

private fun List<EndpointMessageModel>.toMessages(): Map<out String, Message> {
    TODO("Not yet implemented")
}
