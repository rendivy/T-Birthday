package ru.yangel.hackathon.wishlist.item.presentation.ui

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
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
import ru.yangel.hackathon.ui.common.PrimaryButton
import ru.yangel.hackathon.ui.theme.AliceBlue
import ru.yangel.hackathon.ui.theme.CodGray
import ru.yangel.hackathon.ui.theme.Link
import ru.yangel.hackathon.ui.theme.Nevada
import ru.yangel.hackathon.ui.theme.PaddingMedium
import ru.yangel.hackathon.ui.theme.Primary
import ru.yangel.hackathon.ui.theme.PrimaryAlt
import ru.yangel.hackathon.ui.theme.SuvaGray
import ru.yangel.hackathon.ui.theme.Type13
import ru.yangel.hackathon.ui.theme.Type15
import ru.yangel.hackathon.ui.theme.Type20
import ru.yangel.hackathon.wishlist.item.presentation.WishlistItemViewModel
import ru.yangel.hackathon.wishlist.item.presentation.state.WishlistItemState

@Composable
fun WishlistItemScreen(
    itemId: String,
    onBack: () -> Unit = {},
    onNavigateToEdit: () -> Unit = {},
    isFromOwnWishlist: Boolean = true
) {
    val viewModel: WishlistItemViewModel = koinViewModel {
        parametersOf(itemId)
    }
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current
    val screenState by remember { viewModel.state }

    Crossfade(targetState = screenState, label = "") { state ->
        when (state) {
            is WishlistItemState.Loading -> LoadingContent()
            is WishlistItemState.Error -> ErrorContent(onRetry = viewModel::reload)
            is WishlistItemState.Content -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding()
                ) {
                    if (isFromOwnWishlist) {
                        FloatingActionButton(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(all = 24.dp)
                                .zIndex(10f),
                            containerColor = Primary,
                            contentColor = CodGray,
                            shape = RoundedCornerShape(16.dp),
                            onClick = onNavigateToEdit
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.edit_icon),
                                contentDescription = null,
                                tint = CodGray
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        AppTopBar(
                            modifier = Modifier.padding(
                                vertical = 24.dp, horizontal = PaddingMedium
                            ), onIconClick = onBack, iconResId = R.drawable.close_icon
                        )
                        LazyRow(contentPadding = PaddingValues(horizontal = PaddingMedium)) {
                            items(count = state.content.photos?.size ?: 0, key = { index ->
                                state.content.photos?.get(index)?.id ?: index
                            }) {
                                SubcomposeAsyncImage(
                                    modifier = Modifier
                                        .size(120.dp)
                                        .aspectRatio(1f)
                                        .padding(end = 10.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(AliceBlue),
                                    contentScale = ContentScale.Crop,
                                    model = state.content.photos?.get(it)?.link,
                                    contentDescription = null
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(PaddingMedium))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = PaddingMedium),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(10.dp)
                                    .height(20.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (state.content.rating >= 1) Primary else SuvaGray)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Box(
                                modifier = Modifier
                                    .width(10.dp)
                                    .height(20.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (state.content.rating >= 2) Primary else SuvaGray)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Box(
                                modifier = Modifier
                                    .width(10.dp)
                                    .height(20.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (state.content.rating >= 3) PrimaryAlt else SuvaGray)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = when {
                                    state.content.rating >= 1 -> "Желанный подарок"
                                    state.content.rating >= 2 -> "Очень желанный подарок"
                                    state.content.rating >= 3 -> "Мечтает о подарке"
                                    else -> ""
                                }, style = Type15, color = Nevada
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            modifier = Modifier.padding(horizontal = PaddingMedium),
                            text = state.content.name,
                            style = Type20,
                            color = CodGray
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            modifier = Modifier.padding(horizontal = PaddingMedium),
                            text = if (state.content.price != null) "${state.content.price} руб." else "Цена не указана",
                            style = Type13,
                            color = CodGray,
                            maxLines = 1
                        )
                        state.content.link?.let {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = PaddingMedium)
                                    .clickable {
                                        val url =
                                            if (it.startsWith("http://") || it.startsWith("https://")) it else "http://$it"
                                        try {
                                            uriHandler.openUri(url)
                                        } catch (e: Exception) {
                                            Toast
                                                .makeText(
                                                    context,
                                                    "Не удалось открыть ссылку",
                                                    Toast.LENGTH_SHORT
                                                )
                                                .show()
                                        }
                                    },
                                text = "Ссылка на товар",
                                style = Type13,
                                color = Link,
                                maxLines = 1
                            )
                        }
                        state.content.comment?.let {
                            Spacer(modifier = Modifier.height(PaddingMedium))
                            Text(
                                modifier = Modifier.padding(horizontal = PaddingMedium),
                                text = it,
                                style = Type13,
                                color = Nevada
                            )
                        }
                        if (!isFromOwnWishlist) {
                            Spacer(modifier = Modifier.height(PaddingMedium))
                            PrimaryButton(
                                modifier = Modifier
                                    .padding(horizontal = PaddingMedium)
                                    .alpha(if (state.content.isClosed) 0.5f else 1f),
                                text = if (!state.content.isClosed) "Отметить как купленное" else "Куплено",
                                onClick = viewModel::markAsBought
                            )
                        }
                    }
                }
            }
        }
    }
}