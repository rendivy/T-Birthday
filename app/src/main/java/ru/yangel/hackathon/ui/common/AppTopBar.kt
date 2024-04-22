package ru.yangel.hackathon.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import ru.yangel.hackathon.R
import ru.yangel.hackathon.ui.theme.CodGray
import ru.yangel.hackathon.ui.theme.PaddingMedium
import ru.yangel.hackathon.ui.theme.Type24

@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    @DrawableRes iconResId: Int = R.drawable.arrow_back,
    title: String? = null,
    onIconClick: () -> Unit
) {
    Row(modifier = modifier.fillMaxWidth()) {
        IconButton(modifier = Modifier.size(24.dp), onClick = onIconClick) {
            Icon(
                imageVector = ImageVector.vectorResource(iconResId),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
        title?.let {
            Spacer(modifier = Modifier.width(PaddingMedium))
            Text(text = it, style = Type24, color = CodGray)
        }
    }
}