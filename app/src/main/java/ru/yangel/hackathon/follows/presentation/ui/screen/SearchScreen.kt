package ru.yangel.hackathon.follows.presentation.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import ru.yangel.hackathon.R
import ru.yangel.hackathon.follows.presentation.ui.component.AccentTextField
import ru.yangel.hackathon.follows.presentation.ui.component.UserCard
import ru.yangel.hackathon.navigation.utils.noRippleClickable
import ru.yangel.hackathon.splash.presentation.ui.screen.SplashScreen
import ru.yangel.hackathon.ui.theme.CodGray
import ru.yangel.hackathon.ui.theme.PaddingMedium
import ru.yangel.hackathon.ui.theme.Primary
import ru.yangel.hackathon.ui.theme.Type15
import ru.yangel.hackathon.ui.theme.Type24

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview(showBackground = true)
fun SearchScreen(modifier: Modifier = Modifier) {
    val selectedPage = remember { mutableStateOf(0) }
    val pagerState = rememberPagerState(
        initialPage = selectedPage.value,
        initialPageOffsetFraction = 0f
    ) { 3 }
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    val scope = rememberCoroutineScope()
    val textFieldMessage = when (pagerState.currentPage) {
        0 -> "Поиск по людям"
        1 -> "Поиск по командам"
        2 -> "Поиск по филиалам"
        else -> ""
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Поиск",
            style = Type24,
            modifier = Modifier.padding(top = 24.dp, start = 16.dp)
        )
        Spacer(modifier = Modifier.size(24.dp))
        AccentTextField(
            placeHolderValue = textFieldMessage
        )

        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex.value,
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.White,
            indicator = { tabPositions ->
                if (selectedTabIndex.value < tabPositions.size) {
                    SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex.value]),
                        color = Primary
                    )
                }
            }
        ) {
            HomeTabs.entries.forEachIndexed { index, currentTab ->

                Tab(
                    selected = selectedTabIndex.value == index,
                    selectedContentColor = CodGray,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(currentTab.ordinal)
                        }
                    },
                    interactionSource = object : MutableInteractionSource {
                        override val interactions: Flow<Interaction> = emptyFlow()

                        override suspend fun emit(interaction: Interaction) {}

                        override fun tryEmit(interaction: Interaction) = true
                    },
                    text = { Text(text = currentTab.text, style = Type15) },
                )

            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(end = PaddingMedium, top = 24.dp)
        ) { page ->
            when (page) {
                0 -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        repeat(5) {
                            item {
                                UserCard(userName = "Yuriy", photoUrl = "123")
                            }
                        }
                    }
                }

                1 -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        repeat(3) {
                            item {
                                UserCard(userName = "Gay", photoUrl = "123")
                            }
                        }
                    }
                }

                2 -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        repeat(3) {
                            item {
                                UserCard(userName = "Yuriy", photoUrl = "123")
                            }
                        }
                    }
                }
            }

        }
    }
}


enum class HomeTabs(
    val text: String
) {
    Shop(
        text = "Люди"
    ),
    Favourite(
        text = "Команды"
    ),
    Profile(
        text = "Филиалы"
    )
}