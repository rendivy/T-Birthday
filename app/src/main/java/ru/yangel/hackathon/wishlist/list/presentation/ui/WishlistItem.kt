package ru.yangel.hackathon.wishlist.list.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import ru.yangel.hackathon.R
import ru.yangel.hackathon.calendar.presentation.utils.noRippleInteractionSource
import ru.yangel.hackathon.ui.theme.AliceBlue
import ru.yangel.hackathon.ui.theme.Bought
import ru.yangel.hackathon.ui.theme.CodGray
import ru.yangel.hackathon.ui.theme.Nevada
import ru.yangel.hackathon.ui.theme.PaddingMedium
import ru.yangel.hackathon.ui.theme.Primary
import ru.yangel.hackathon.ui.theme.PrimaryAlt
import ru.yangel.hackathon.ui.theme.SuvaGray
import ru.yangel.hackathon.ui.theme.Type13
import ru.yangel.hackathon.ui.theme.Type20

@Composable
fun WishlistItem(
    modifier: Modifier = Modifier,
    name: String = "",
    price: String = "",
    isClosed: Boolean = false,
    imageUrl: String = "",
    priority: Int = 3,
    onItemClicked: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                onClick = onItemClicked,
                interactionSource = noRippleInteractionSource,
                indication = null
            )
            .clip(RoundedCornerShape(12.dp))
            .background(AliceBlue)
            .padding(16.dp)
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .size(64.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp)),
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(PaddingMedium))
        Column(
            modifier = Modifier
                .height(64.dp)
                .weight(1f),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = name,
                style = Type20,
                color = CodGray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = if (!isClosed) price else "Куплено",
                style = Type13,
                color = if (!isClosed) Nevada else Bought,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Column(
            modifier = Modifier.height(64.dp), horizontalAlignment = Alignment.End
        ) {
            Spacer(modifier = Modifier.height(6.dp))
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = ImageVector.vectorResource(R.drawable.chevron_right),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Box(
                    modifier = Modifier
                        .width(10.dp)
                        .height(20.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (priority >= 1) Primary else SuvaGray)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Box(
                    modifier = Modifier
                        .width(10.dp)
                        .height(20.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (priority >= 2) Primary else SuvaGray)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Box(
                    modifier = Modifier
                        .width(10.dp)
                        .height(20.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (priority >= 3) PrimaryAlt else SuvaGray)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}