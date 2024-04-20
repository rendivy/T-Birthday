package ru.yangel.hackathon.onBoarding.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.yangel.hackathon.R
import ru.yangel.hackathon.ui.theme.Primary
import ru.yangel.hackathon.ui.theme.Roboto


@Composable
@Preview(showBackground = true)
fun OnBoardingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Primary),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_cake),
            contentScale = ContentScale.Crop,
            modifier = Modifier,
            contentDescription = null
        )
        Text(
            text = "Поздравь своего коллегу так, чтобы ему понравилось!",
            modifier = Modifier.padding(start = 32.dp, end = 32.dp),
            fontFamily = Roboto,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(100.dp))
    }
}