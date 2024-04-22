package ru.yangel.hackathon.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import ru.yangel.hackathon.ui.theme.CodGray
import ru.yangel.hackathon.ui.theme.Primary
import ru.yangel.hackathon.ui.theme.Type15

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    @DrawableRes trailingIconResId: Int? = null,
) {
    Button(
        modifier = modifier.fillMaxWidth().height(56.dp), onClick = onClick, colors = ButtonDefaults.buttonColors(
            containerColor = Primary,
            contentColor = CodGray,
            disabledContainerColor = Primary,
            disabledContentColor = CodGray
        ), shape = RoundedCornerShape(7.dp)
    ) {
        Text(text = text, style = Type15)
        trailingIconResId?.let {
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                imageVector = ImageVector.vectorResource(it), contentDescription = null
            )
        }
    }
}