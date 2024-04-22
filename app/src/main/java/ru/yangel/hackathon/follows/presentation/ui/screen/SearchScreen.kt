package ru.yangel.hackathon.follows.presentation.ui.screen

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import ru.yangel.hackathon.follows.presentation.ui.component.AccentTextField
import ru.yangel.hackathon.follows.presentation.ui.component.FillialCard
import ru.yangel.hackathon.follows.presentation.ui.component.UserCard
import ru.yangel.hackathon.follows.presentation.viewmodel.AffiliatesState
import ru.yangel.hackathon.follows.presentation.viewmodel.SearchViewModel
import ru.yangel.hackathon.follows.presentation.viewmodel.TeamState
import ru.yangel.hackathon.follows.presentation.viewmodel.UsersState
import ru.yangel.hackathon.ui.theme.CodGray
import ru.yangel.hackathon.ui.theme.Primary
import ru.yangel.hackathon.ui.theme.SuvaGray
import ru.yangel.hackathon.ui.theme.Type15
import ru.yangel.hackathon.ui.theme.Type24

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    rootNavController: NavController,
    searchViewModel: SearchViewModel = koinViewModel()
) {
    val selectedPage = remember { mutableStateOf(0) }
    val pagerState = rememberPagerState(
        initialPage = selectedPage.value,
        initialPageOffsetFraction = 0f
    ) { 3 }
    val state by searchViewModel.state.collectAsStateWithLifecycle()
    val usersState by searchViewModel.usersState.collectAsStateWithLifecycle()
    val commandState by searchViewModel.teamState.collectAsStateWithLifecycle()
    val affiliatesState by searchViewModel.affiliatesState.collectAsStateWithLifecycle()

    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    val scope = rememberCoroutineScope()
    val textFieldMessage = when (pagerState.currentPage) {
        0 -> "Поиск по людям"
        1 -> "Поиск по командам"
        2 -> "Поиск по филиалам"
        else -> ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = "Поиск",
            style = Type24,
            modifier = Modifier.padding(top = 24.dp, start = 16.dp)
        )
        Spacer(modifier = Modifier.size(24.dp))
        AccentTextField(
            textFieldValue = state,
            onValueChange = {
                searchViewModel.onStateChange(it);
                when (pagerState.currentPage) {
                    0 -> searchViewModel.searchUsersByName(it)
                    1 -> searchViewModel.searchCommand(it)
                    2 -> searchViewModel.searchAffiliate(it)
                }
            },
            placeHolderValue = textFieldMessage
        )


        ScrollableTabRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center),
            selectedTabIndex = selectedTabIndex.value,
            containerColor = Color.White,
            indicator = { tabPositions ->
                if (selectedTabIndex.value < tabPositions.size) {
                    SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex.value]),
                        color = Primary
                    )
                }
            },
            divider = {  }
        ) {
            ru.yangel.hackathon.real_follows.ui.screen.HomeTabs.entries.forEachIndexed { index, currentTab ->

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
            modifier = Modifier.padding(top = 24.dp)
        ) { page ->
            when (page) {
                0 -> {
                    when (usersState) {
                        UsersState.Initial -> {
                            SearchPlaceHolder()
                        }
                        UsersState.Loading -> ru.yangel.hackathon.real_follows.ui.screen.YellowLoader()
                        is UsersState.Success -> {
                            val content = (usersState as UsersState.Success).users
                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                                content.forEach { userResponse ->
                                    item {
                                        UserCard(
                                            userName = userResponse.fullName,
                                            onClick = { rootNavController.navigate("profileDetails/${userResponse.id}") },
                                            photoUrl = userResponse.photoUrl
                                        )
                                    }
                                }
                            }
                        }

                        is UsersState.Error -> {
                            searchViewModel.resetUsersState()
                        }
                    }
                }

                1 -> {
                    when (commandState) {
                        TeamState.Initial -> SearchPlaceHolder()
                        TeamState.Loading -> ru.yangel.hackathon.real_follows.ui.screen.YellowLoader()
                        is TeamState.Success -> {
                            val content = (commandState as TeamState.Success).users
                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                                content.forEach {
                                    item {
                                        FillialCard(
                                            fillial = it.name
                                        )
                                    }
                                }
                            }
                        }

                        is TeamState.Error -> {
                            searchViewModel.resetTeamState()
                        }
                    }
                }

                2 -> {
                    when (affiliatesState) {
                        AffiliatesState.Initial -> SearchPlaceHolder()
                        AffiliatesState.Loading -> ru.yangel.hackathon.real_follows.ui.screen.YellowLoader()
                        is AffiliatesState.Success -> {
                            val content = (affiliatesState as AffiliatesState.Success).users
                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                                content.forEach {
                                    item {
                                        FillialCard(
                                            fillial = it.name
                                        )
                                    }
                                }
                            }
                        }
                        else -> {
                            searchViewModel.resetAffiliatesState()
                        }
                    }
                }

            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun YellowLoader() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = Primary,
            modifier = Modifier.size(32.dp),
            strokeWidth = 3.dp
        )
    }
}


fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition(label = "")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
        ), label = ""
    )
    background(
        brush = Brush.linearGradient(
            colors = listOf(
                SuvaGray,
                Primary,
                SuvaGray
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
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