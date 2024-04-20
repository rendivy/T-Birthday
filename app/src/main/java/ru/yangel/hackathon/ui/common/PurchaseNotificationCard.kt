package ru.yangel.hackathon.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.yangel.hackathon.R
import ru.yangel.hackathon.ui.theme.Roboto
import ru.yangel.hackathon.ui.theme.RobotoFlex

@Composable
fun PurchaseNotificationCard(
    username: String = "Гордей",
    purchase: String = "Logitech MX Master 3S"
) {
    val bottomHint = buildAnnotatedString {
        append("Пользователь ")
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.SemiBold
            )
        ) {
            append(username)
            append(" ")
        }
        append("отметил подарок ")
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.SemiBold
            )
        ) {
            append(purchase)
            append(" ")
        }
        append("как купленный")
    }
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
    ) {
        Text(
            text = bottomHint, fontSize = 13.sp, modifier = Modifier.padding(
                start = 12.dp,
                end = 12.dp,
                top = 8.dp,
                bottom = 8.dp
            ),
            fontFamily = RobotoFlex,
            fontWeight = FontWeight.Normal
        )
    }
}