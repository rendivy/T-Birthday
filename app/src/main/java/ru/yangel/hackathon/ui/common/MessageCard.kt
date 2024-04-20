package ru.yangel.hackathon.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import ru.yangel.hackathon.ui.theme.AliceBlue
import ru.yangel.hackathon.ui.theme.Primary

@Composable
@Preview(showBackground = true)
fun MessageCard(profileIconURL: String = "https://media.tenor.com/qn8JutHbzYgAAAAM/cat-cute.gif", username: String = "", text: String = "" ) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White),
        verticalAlignment = Alignment.Top,
    ) {
        SubcomposeAsyncImage(
            model = profileIconURL,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp, start = 12.dp)
                .size(64.dp)
                .clip(CircleShape)
        )
        Column(modifier = Modifier.padding(start = 8.dp, top = 14.dp)) {
            Text(
                text = username,
                fontSize = 16.sp,
                color = Primary,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .fillMaxWidth()
            )
            Text(
                text = text,
                fontSize = 13.sp,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun Test() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AliceBlue)
    ) {
        repeat(5) {
            item {
                MessageCard()
            }
        }
        item {
            UserMessageCard()
        }
        item {
            PurchaseNotificationCard()
        }
        repeat(5) {
            item {
                MessageCard()
            }
        }

    }
}