package ru.yangel.hackathon.chat.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.yangel.hackathon.R
import ru.yangel.hackathon.navigation.utils.noRippleClickable
import ru.yangel.hackathon.ui.theme.Nevada
import ru.yangel.hackathon.ui.theme.PaddingMedium
import ru.yangel.hackathon.ui.theme.Type15Search

@Composable
fun BottomText(
    modifier: Modifier = Modifier,
    textFieldValue: String = "",
    singleLine: Boolean = true,
    onClick: () -> Unit = {},
    placeHolderValue: String = "Введите сообщение...",
    onValueChange: (String) -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    BasicTextField(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(7.dp)
            )
            .fillMaxWidth(),
        value = textFieldValue,
        onValueChange = onValueChange,
        textStyle = Type15Search,
        keyboardOptions = keyboardOptions,
        singleLine = singleLine,
        enabled = true,
        cursorBrush = SolidColor(Color.White),
        decorationBox = @Composable { innerTextField ->
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(
                        start = PaddingMedium,
                        bottom = PaddingMedium,
                        top = PaddingMedium,
                        end = PaddingMedium
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.chevron_right),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .noRippleClickable { onClick() },
                    tint = Nevada,
                    contentDescription = null,
                )
                innerTextField()
                if (textFieldValue.isEmpty()) {
                    Text(
                        text = placeHolderValue,
                        textAlign = TextAlign.Start,
                        style = Type15Search
                    )
                }
            }
        }
    )
}