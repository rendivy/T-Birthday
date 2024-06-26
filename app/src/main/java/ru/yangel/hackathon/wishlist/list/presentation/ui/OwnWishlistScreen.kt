package ru.yangel.hackathon.wishlist.list.presentation.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.yangel.hackathon.R
import ru.yangel.hackathon.ui.common.ErrorContent
import ru.yangel.hackathon.ui.common.LoadingContent
import ru.yangel.hackathon.ui.theme.CodGray
import ru.yangel.hackathon.ui.theme.PaddingMedium
import ru.yangel.hackathon.ui.theme.Primary
import ru.yangel.hackathon.ui.theme.Type24
import ru.yangel.hackathon.wishlist.list.presentation.WishListViewModel
import ru.yangel.hackathon.wishlist.list.presentation.state.WishListState

@Composable
fun OwnWishListScreen(
    onItemClick: (String) -> Unit,
    onAddClick: () -> Unit
) {
    val viewModel: WishListViewModel = koinViewModel {
        parametersOf("")
    }
    val state by remember { viewModel.state }
    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(all = 24.dp)
            .zIndex(10f),
            containerColor = Primary,
            contentColor = CodGray,
            shape = RoundedCornerShape(16.dp),
            onClick = onAddClick) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.add_icon),
                contentDescription = null
            )
        }
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.padding(horizontal = PaddingMedium, vertical = 24.dp),
                text = "Список желаний",
                color = CodGray,
                style = Type24
            )
            Crossfade(targetState = state, label = "") {
                when (it) {
                    is WishListState.Data -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = PaddingMedium)
                        ) {
                            items(it.items.size, key = { index -> it.items[index].id }) { index ->
                                val item = it.items[index]
                                WishlistItem(
                                    modifier = Modifier.padding(bottom = 20.dp),
                                    name = item.name,
                                    price = if (item.price == null) "Цена не указана" else "${item.price} руб.",
                                    priority = item.rating,
                                    isClosed = item.isClosed,
                                    imageUrl = item.mainPhoto?.link ?: "",
                                    onItemClicked = { onItemClick(item.id) }
                                )
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