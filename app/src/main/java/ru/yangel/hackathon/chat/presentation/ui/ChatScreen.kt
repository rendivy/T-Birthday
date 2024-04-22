package ru.yangel.hackathon.chat.presentation.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.yangel.hackathon.R
import ru.yangel.hackathon.chat.presentation.ChatViewModel
import ru.yangel.hackathon.chat.presentation.state.ChatState
import ru.yangel.hackathon.ui.common.AppTopBar
import ru.yangel.hackathon.ui.common.ErrorContent
import ru.yangel.hackathon.ui.common.LoadingContent
import ru.yangel.hackathon.ui.common.MessageCard
import ru.yangel.hackathon.ui.common.PurchaseNotificationCard
import ru.yangel.hackathon.ui.common.UserMessageCard
import ru.yangel.hackathon.ui.theme.AliceBlue

@Composable
fun ChatScreen(chatId: String, name: String, onBack: () -> Unit = {}) {
    val viewModel: ChatViewModel = koinViewModel {
        parametersOf(chatId)
    }
    val state by remember { viewModel.screenState }
    val content = remember { viewModel.messagesList }
    var value by remember { viewModel.message }
    Crossfade(targetState = state, label = "") {
        when (it) {
            ChatState.Loading -> LoadingContent()
            ChatState.Content -> {
                Column(modifier = Modifier.fillMaxSize().systemBarsPadding()) {
                    AppTopBar(modifier = Modifier.padding(horizontal = 8.dp, vertical = 22.dp), iconResId = R.drawable.close_icon, title = name) {
                        onBack()
                    }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(AliceBlue)
                    ) {
                        items(count = content.size) {
                            val contentItem = content[it]
                            if (contentItem.isNotification) {
                                PurchaseNotificationCard(
                                    username = contentItem.senderName,
                                    purchase = contentItem.content
                                )
                            } else if (contentItem.isOwnMessage) {
                                UserMessageCard(userMessage = contentItem.content)
                            } else {
                                MessageCard(
                                    profileIconURL = contentItem.senderAvatarUrl,
                                    username = contentItem.senderName,
                                    text = contentItem.content
                                )
                            }
                        }
                    }
                    BottomText(
                        textFieldValue = value,
                        onValueChange = { value = it },
                        onClick = viewModel::sendMessage
                    )
                }
            }

            ChatState.Error -> ErrorContent(onRetry = viewModel::reload)
        }
    }
}