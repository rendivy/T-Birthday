package ru.yangel.hackathon.follows.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import ru.yangel.hackathon.R
import ru.yangel.hackathon.follows.presentation.ui.screen.shimmerEffect
import ru.yangel.hackathon.navigation.utils.noRippleClickable
import ru.yangel.hackathon.ui.theme.AliceBlue
import ru.yangel.hackathon.ui.theme.RobotoFlex
import ru.yangel.hackathon.ui.theme.SuvaGray
import ru.yangel.hackathon.ui.theme.Type15
import ru.yangel.hackathon.ui.theme.Type20
import ru.yangel.hackathon.ui.theme.Type24

@Composable
fun UserCard(userName: String, photoUrl: String, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .noRippleClickable { onClick() }
            .background(AliceBlue),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        SubcomposeAsyncImage(
            model = photoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp, start = 12.dp, end = 16.dp)
                .size(84.dp)
                .clip(CircleShape)
        ) {
            val state = painter.state
            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                Box(modifier = Modifier
                    .size(84.dp)
                    .clip(CircleShape)
                    .shimmerEffect())
            } else {
                SubcomposeAsyncImageContent()
            }

        }
        Text(
            text = userName,
            style = Type15
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.forward_arrow_icon),
            modifier = Modifier.padding(start = 24.dp, end = 18.dp),
            contentDescription = null
        )
    }

}

@Composable
fun FillialCard(fillial: String, isLiked: MutableState<Boolean> = mutableStateOf(false)) {
    val icon = if (!isLiked.value) R.drawable.follows_icon else R.drawable.filled_ic
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                isLiked.value = !isLiked.value
            }
            .background(AliceBlue),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = fillial,
            fontSize = 13.sp,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp, start = 12.dp, end = 12.dp)
                .weight(1f),
            fontFamily = RobotoFlex,
            fontWeight = FontWeight.Normal,
        )
        Icon(
            painter = painterResource(id = icon),
            modifier = Modifier
                .padding(start = 24.dp, end = 18.dp),
            tint = SuvaGray,
            contentDescription = null
        )
    }

}