package ru.yangel.hackathon.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.yangel.hackathon.R
import ru.yangel.hackathon.ui.theme.CodGray
import ru.yangel.hackathon.ui.theme.PaddingMedium
import ru.yangel.hackathon.ui.theme.Primary
import ru.yangel.hackathon.ui.theme.Type15

@Composable
fun ErrorContent(onRetry: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp, vertical = 10.dp)
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(100.dp),
                imageVector = ImageVector.vectorResource(R.drawable.loading_error),
                contentDescription = null,
                tint = Primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Произошла ошибка при загрузке данных, проверьте подключение к сети",
                style = Type15,
                textAlign = TextAlign.Center,
                color = CodGray
            )
            Spacer(modifier = Modifier.height(36.dp))
            PrimaryButton(
                modifier = Modifier.padding(PaddingMedium),
                text = "Повторить",
                onClick = onRetry
            )
//            Button(
//                modifier = Modifier
//                    .width(124.dp)
//                    .height(45.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Primary,
//                    contentColor = CodGray
//                ),
//                shape = RoundedCornerShape(100.dp),
//                onClick = onRetry
//            ) {
//                Text(
//                    text = "Повторить",
//                    style = Type15,
//                    textAlign = TextAlign.Center,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis
//                )
//            }
        }
    }
}