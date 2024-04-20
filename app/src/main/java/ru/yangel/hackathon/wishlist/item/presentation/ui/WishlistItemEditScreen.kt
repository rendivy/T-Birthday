package ru.yangel.hackathon.wishlist.item.presentation.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.yangel.hackathon.R
import ru.yangel.hackathon.ui.common.AppOutlinedTextField
import ru.yangel.hackathon.ui.common.AppTopBar
import ru.yangel.hackathon.ui.common.ErrorContent
import ru.yangel.hackathon.ui.common.LoadingContent
import ru.yangel.hackathon.ui.common.ObserveAsEvents
import ru.yangel.hackathon.ui.common.PrimaryButton
import ru.yangel.hackathon.ui.theme.CodGray
import ru.yangel.hackathon.ui.theme.PaddingMedium
import ru.yangel.hackathon.ui.theme.Primary
import ru.yangel.hackathon.wishlist.item.presentation.WishlistItemEditViewModel
import ru.yangel.hackathon.wishlist.item.presentation.state.WishlistItemEditState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WishlistItemEditScreen(
    itemId: String = "ce5546ed-d982-466d-a1ba-07d702381637", onSuccess: () -> Unit = {}
) {
    val viewModel: WishlistItemEditViewModel = koinViewModel {
        parametersOf(itemId)
    }
    ObserveAsEvents(flow = viewModel.successEvents) { onSuccess() }
    val imageUris = remember { viewModel.imageUris }
    val existingImages = remember { viewModel.existingImages }
    val addLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uri: List<Uri> ->
            imageUris.addAll(uri)
        })
    val content by remember { viewModel.content }
    val screenState by remember { viewModel.state }
    val isDataCorrectlyFilled by remember { viewModel.canSubmit }

    Crossfade(targetState = screenState, label = "") { state ->
        when (state) {
            WishlistItemEditState.Loading -> LoadingContent()
            WishlistItemEditState.Content -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    FloatingActionButton(modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .alpha(if (isDataCorrectlyFilled) 1f else 0.5f)
                        .padding(all = 24.dp)
                        .zIndex(10f),
                        containerColor = Primary,
                        contentColor = CodGray,
                        shape = RoundedCornerShape(16.dp),
                        onClick = { if (isDataCorrectlyFilled) viewModel.submit() }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.apply_icon),
                            contentDescription = null
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        AppTopBar(
                            modifier = Modifier.padding(
                                vertical = 24.dp, horizontal = PaddingMedium
                            )
                        ) {}
                        LazyRow(contentPadding = PaddingValues(horizontal = PaddingMedium)) {
                            items(count = existingImages.size) {
                                Image(
                                    modifier = Modifier
                                        .size(120.dp)
                                        .aspectRatio(1f)
                                        .padding(end = 10.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .combinedClickable(onClick = {}, onLongClick = {
                                            viewModel.deletedPhotoIds.add(existingImages[it].id)
                                            viewModel.existingImages.removeAt(it)
                                        }),
                                    painter = rememberAsyncImagePainter(model = existingImages[it]),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop
                                )
                            }
                            items(count = imageUris.size) {
                                Image(
                                    modifier = Modifier
                                        .size(120.dp)
                                        .aspectRatio(1f)
                                        .padding(end = 10.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .combinedClickable(onClick = {}, onLongClick = {
                                            viewModel.imageUris.removeAt(it)
                                        }),
                                    painter = rememberAsyncImagePainter(model = imageUris[it]),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(PaddingMedium))
                        PrimaryButton(text = "Загрузить фотографию",
                            trailingIconResId = R.drawable.add_icon,
                            modifier = Modifier.padding(horizontal = PaddingMedium),
                            onClick = {
                                addLauncher.launch("image/*")
                            })
                        Spacer(modifier = Modifier.height(PaddingMedium))
                        AppOutlinedTextField(
                            modifier = Modifier.padding(horizontal = PaddingMedium),
                            value = content.name,
                            onValueChange = viewModel::setName,
                            label = "Название*",
                            placeholder = "Введите название подарка",
                            isError = !content.isNameCorrect
                        )
                        Spacer(modifier = Modifier.height(PaddingMedium))
                        AppOutlinedTextField(
                            modifier = Modifier.padding(horizontal = PaddingMedium),
                            value = content.price,
                            onValueChange = viewModel::setPrice,
                            label = "Стоимость",
                            placeholder = "Введите стоимость подарка",
                            keyboardType = KeyboardType.Number,
                            isError = !content.isPriceCorrect
                        )
                        Spacer(modifier = Modifier.height(PaddingMedium))
                        AppOutlinedTextField(
                            modifier = Modifier.padding(horizontal = PaddingMedium),
                            value = content.link,
                            onValueChange = viewModel::setLink,
                            label = "Ссылка",
                            placeholder = "Введите ссылку на покупку подарка",
                            keyboardType = KeyboardType.Uri
                        )
                        Spacer(modifier = Modifier.height(PaddingMedium))
                        AppOutlinedTextField(
                            modifier = Modifier.padding(horizontal = PaddingMedium),
                            value = content.priority,
                            onValueChange = viewModel::setPriority,
                            label = "Приоритет (от 1 до 3)*",
                            placeholder = "Насколько сильно вы хотите подарок",
                            keyboardType = KeyboardType.Number,
                            isError = !content.isPriorityCorrect
                        )
                        Spacer(modifier = Modifier.height(PaddingMedium))
                        AppOutlinedTextField(
                            modifier = Modifier
                                .padding(horizontal = PaddingMedium)
                                .defaultMinSize(minHeight = 120.dp),
                            value = content.comment,
                            onValueChange = viewModel::setComment,
                            label = "Комментарий",
                            placeholder = "Введите комментарий...",
                            multiLine = true
                        )
                    }
                }
            }

            WishlistItemEditState.Error -> ErrorContent(onRetry = viewModel::reload)
        }
    }
}