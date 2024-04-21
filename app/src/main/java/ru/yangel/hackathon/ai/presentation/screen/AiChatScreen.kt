package ru.yangel.hackathon.ai.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.yangel.hackathon.R
import ru.yangel.hackathon.ai.viewModel.AiState
import ru.yangel.hackathon.ai.viewModel.AiViewModel
import ru.yangel.hackathon.follows.presentation.ui.component.AiTextField
import ru.yangel.hackathon.follows.presentation.ui.screen.YellowLoader
import ru.yangel.hackathon.navigation.utils.noRippleClickable
import ru.yangel.hackathon.ui.theme.AliceBlue
import ru.yangel.hackathon.ui.theme.CodGray
import ru.yangel.hackathon.ui.theme.PaddingMedium
import ru.yangel.hackathon.ui.theme.Primary
import ru.yangel.hackathon.ui.theme.Type15Search
import ru.yangel.hackathon.ui.theme.Type24

@Composable
@Preview(showBackground = true)
fun AiChatScreen(aiViewModel: AiViewModel = koinViewModel()) {

    val aiState by aiViewModel.userState.collectAsStateWithLifecycle()
    val uiState by aiViewModel.userUi.collectAsStateWithLifecycle()


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, start = 16.dp, bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.arrow_back),
                    modifier = Modifier.noRippleClickable { },
                    contentDescription = null,
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(PaddingMedium))
                Text(text = "Генерация идей", style = Type24, color = CodGray)
            }
        },
        bottomBar = {
            AiTextField(
                textFieldValue = uiState,
                onValueChange = { aiViewModel.updateUi(it) },
                onClick = { aiViewModel.getMessage() }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(AliceBlue)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            when (aiState) {
                is AiState.Initial -> {
                    Text(
                        text = "Введите увлечения человека и получите идеи для того, что можно подарить!",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        textAlign = TextAlign.Center,
                        style = Type15Search
                    )
                }

                is AiState.Content -> {
                    val text = (aiState as AiState.Content).message
                    AiCard(text = text)
                }

                is AiState.Loading -> {
                    YellowLoader()
                }

                else -> {}
            }

        }
    }
}

@Composable
fun AiCard(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White),
        verticalAlignment = Alignment.Top,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.background_cake),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp, start = 12.dp)
                .size(64.dp)
                .clip(CircleShape)
        )
        Column(modifier = Modifier.padding(start = 8.dp, top = 14.dp)) {
            Text(
                text = "Ассистент Олег",
                fontSize = 16.sp,
                color = Primary,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .fillMaxWidth()
            )
            CustomText(
                text = text,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .fillMaxWidth()
            )
        }
    }
}


val boldRegex = Regex("(?<!\\*)\\*\\*(?!\\*).*?(?<!\\*)\\*\\*(?!\\*)")


@Composable
fun CustomText(text: String, modifier: Modifier = Modifier) {

    var results: MatchResult? = boldRegex.find(text)

    val boldIndexes = mutableListOf<Pair<Int, Int>>()

    val keywords = mutableListOf<String>()

    var finalText = text

    while (results != null) {
        keywords.add(results.value)
        results = results.next()
    }

    keywords.forEach { keyword ->
        val indexOf = finalText.indexOf(keyword)
        val newKeyWord = keyword.removeSurrounding("**")
        finalText = finalText.replace(keyword, newKeyWord)
        boldIndexes.add(Pair(indexOf, indexOf + newKeyWord.length))
    }

    val annotatedString = buildAnnotatedString {
        append(finalText)

        // Add bold style to keywords that has to be bold
        boldIndexes.forEach {
            addStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.roboto_bold)),
                    fontSize = 15.sp

                ),
                start = it.first,
                end = it.second
            )

        }
    }

    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 12.dp, bottom = 12.dp),
        fontSize = 16.sp,
        text = annotatedString
    )
}