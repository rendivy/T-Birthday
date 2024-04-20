package ru.yangel.hackathon.wishlist.item.presentation.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import ru.yangel.hackathon.R
import ru.yangel.hackathon.ui.common.AppOutlinedTextField
import ru.yangel.hackathon.ui.common.AppTopBar
import ru.yangel.hackathon.ui.common.PrimaryButton
import ru.yangel.hackathon.ui.theme.CodGray
import ru.yangel.hackathon.ui.theme.PaddingMedium
import ru.yangel.hackathon.ui.theme.Primary

@Composable
fun WishlistItemEditScreen() {
    val imageUris = remember { mutableStateListOf<Uri>() }
    val addLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uri: List<Uri> ->
            imageUris.addAll(uri)
        })
    var name by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var link by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(all = 24.dp)
            .zIndex(10f),
            containerColor = Primary,
            contentColor = CodGray,
            shape = RoundedCornerShape(16.dp),
            onClick = { /*TODO*/ }) {
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
                modifier = Modifier.padding(vertical = 24.dp, horizontal = PaddingMedium)
            ) {}
            LazyRow(contentPadding = PaddingValues(horizontal = PaddingMedium)) {
                items(count = imageUris.size) {
                    Image(
                        modifier = Modifier
                            .size(120.dp)
                            .aspectRatio(1f)
                            .padding(end = 10.dp)
                            .clip(RoundedCornerShape(8.dp)),
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
                value = name,
                onValueChange = { name = it },
                label = "Название*",
                placeholder = "Введите название подарка"
            )
            Spacer(modifier = Modifier.height(PaddingMedium))
            AppOutlinedTextField(
                modifier = Modifier.padding(horizontal = PaddingMedium),
                value = price,
                onValueChange = { price = it },
                label = "Стоимость",
                placeholder = "Введите стоимость подарка",
                keyboardType = KeyboardType.Number
            )
            Spacer(modifier = Modifier.height(PaddingMedium))
            AppOutlinedTextField(
                modifier = Modifier.padding(horizontal = PaddingMedium),
                value = link,
                onValueChange = { link = it },
                label = "Ссылка",
                placeholder = "Введите ссылку на покупку подарка",
                keyboardType = KeyboardType.Uri
            )
            Spacer(modifier = Modifier.height(PaddingMedium))
            AppOutlinedTextField(
                modifier = Modifier.padding(horizontal = PaddingMedium),
                value = priority,
                onValueChange = { priority = it },
                label = "Приоритет (от 1 до 3)*",
                placeholder = "Насколько сильно вы хотите подарок",
                keyboardType = KeyboardType.Number
            )
            Spacer(modifier = Modifier.height(PaddingMedium))
            AppOutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = PaddingMedium)
                    .defaultMinSize(minHeight = 120.dp),
                value = comment,
                onValueChange = { comment = it },
                label = "Комментарий",
                placeholder = "Введите комментарий...",
                multiLine = true
            )
        }
    }
}