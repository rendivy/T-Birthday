package ru.yangel.hackathon.onBoarding.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.yangel.hackathon.R
import ru.yangel.hackathon.navigation.utils.noRippleClickable
import ru.yangel.hackathon.ui.theme.Primary


@Composable
fun OnBoardingScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Primary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_cake),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Поздравь своего коллегу\nтак, чтобы ему понравилось!",
            modifier = Modifier
                .padding(start = 32.dp, end = 32.dp)
                .fillMaxWidth(),
            fontFamily = FontFamily(Font(R.font.roboto_bold)),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
                .noRippleClickable { navController.navigate("login") },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Начать",
                modifier = Modifier
                    .padding(start = 32.dp, end = 16.dp),
                fontFamily = FontFamily(Font(R.font.roboto_bold)),
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            )
            Image(
                painter = painterResource(id = R.drawable.continue_arrow),
                modifier = Modifier.size(16.dp),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.weight(1.2f))
    }
}