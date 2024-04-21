package ru.yangel.hackathon.wishlist.list.presentation.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.SubcomposeAsyncImage
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.yangel.hackathon.R
import ru.yangel.hackathon.ui.common.AppTopBar
import ru.yangel.hackathon.ui.common.ErrorContent
import ru.yangel.hackathon.ui.common.LoadingContent
import ru.yangel.hackathon.ui.theme.AliceBlue
import ru.yangel.hackathon.ui.theme.CodGray
import ru.yangel.hackathon.ui.theme.Nevada
import ru.yangel.hackathon.ui.theme.PaddingMedium
import ru.yangel.hackathon.ui.theme.Primary
import ru.yangel.hackathon.ui.theme.Type20
import ru.yangel.hackathon.wishlist.list.presentation.WishListViewModel
import ru.yangel.hackathon.wishlist.list.presentation.state.WishListState

@Composable
fun OtherWishListScreen(
    userId: String,
    onItemClick: (String) -> Unit,
    name: String,
    birthDate: String,
    avatarUrl: String,
    onBack: () -> Unit,
    onNavigateToChat: () -> Unit
) {
    val viewModel: WishListViewModel = koinViewModel {
        parametersOf(userId)
    }
    val state by remember { viewModel.state }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(all = 24.dp)
                .zIndex(10f),
            containerColor = Primary,
            contentColor = CodGray,
            shape = RoundedCornerShape(16.dp),
            onClick = onNavigateToChat
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.chat_icon),
                contentDescription = null
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AppTopBar(
                modifier = Modifier.padding(
                    vertical = 24.dp, horizontal = PaddingMedium
                ), onIconClick = onBack, iconResId = R.drawable.close_icon, title = "Список желаний"
            )
            Row(modifier = Modifier.padding(horizontal = PaddingMedium)) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .size(64.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(AliceBlue),
                    model = avatarUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.size(PaddingMedium))
                Column(
                    modifier = Modifier.height(64.dp), verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = "Пользователя", style = Type20, color = Nevada, maxLines = 1)
                    Text(text = "$name, $birthDate", style = Type20, color = CodGray, maxLines = 1)
                }
            }
            Spacer(modifier = Modifier.size(24.dp))
            Crossfade(targetState = state, label = "") {
                when (it) {
                    is WishListState.Data -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = PaddingMedium)
                        ) {
                            items(it.items.size, key = { index -> it.items[index].id }) { index ->
                                val item = it.items[index]
                                WishlistItem(modifier = Modifier.padding(bottom = 20.dp),
                                    name = item.name,
                                    price = if (item.price == null) "Цена не указана" else "${item.price} руб.",
                                    priority = item.rating,
                                    isClosed = item.isClosed,
                                    imageUrl = item.mainPhoto?.link ?: "",
                                    onItemClicked = { onItemClick(item.id) })
                            }
                        }
                    }

                    WishListState.Error -> ErrorContent(onRetry = viewModel::loadList)
                    WishListState.Loading -> LoadingContent()
                }
            }
        }
    }
}