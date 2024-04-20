package ru.yangel.hackathon.calendar.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.yangel.hackathon.calendar.presentation.screen.components.CalendarContent
import ru.yangel.hackathon.calendar.presentation.state.CalendarUiState
import ru.yangel.hackathon.calendar.presentation.viewmodel.CalendarViewModel
import ru.yangel.hackathon.follows.presentation.ui.component.UserCard
import ru.yangel.hackathon.ui.common.ErrorContent
import ru.yangel.hackathon.ui.common.LoadingContent
import ru.yangel.hackathon.ui.theme.CodGray
import ru.yangel.hackathon.ui.theme.PaddingMedium
import ru.yangel.hackathon.ui.theme.PaddingRegular
import ru.yangel.hackathon.ui.theme.PaddingSmall
import ru.yangel.hackathon.ui.theme.Type20
import ru.yangel.hackathon.ui.theme.Type24
import ru.yangel.hackathon.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = koinViewModel()
) {
    val state by viewModel.calendarUiState.collectAsStateWithLifecycle()
    val calendarBottomSheetState by viewModel.sheetState.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState()

    when (state) {
        CalendarUiState.Initial -> Unit
        CalendarUiState.Loading -> LoadingContent()
        CalendarUiState.Error -> ErrorContent()
        is CalendarUiState.Content -> {
            val birthdays = (state as CalendarUiState.Content).birthdays.map {
                it.calendarDay
            }
            CalendarContent(
                birthdays = birthdays,
                onBirthdayClick = viewModel::onBirthdayClick,
                modifier = modifier
            )
        }
    }

    if (calendarBottomSheetState.isShown) {
        ModalBottomSheet(
            containerColor = White,
            sheetState = sheetState,
            onDismissRequest = viewModel::onDismissBottomSheet
        ) {
            Column(
                modifier = Modifier.padding(horizontal = PaddingMedium)
            ) {
                Spacer(modifier = Modifier.height(PaddingSmall))

                Text(
                    text = calendarBottomSheetState.date ?: "",
                    style = Type24,
                    color = CodGray
                )

                Spacer(modifier = Modifier.height(PaddingRegular))

                Text(
                    text = "Дни рождения:",
                    style = Type20,
                    color = CodGray
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(PaddingSmall)
                ) {
                    items(calendarBottomSheetState.birthdays) { birthday ->
                        UserCard(
                            userName = birthday.fullName,
                            photoUrl = birthday.photoUrl,
                            onClick = {
                                // TODO navigate to profile (pass id)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(PaddingMedium))
            }
        }
    }
}

@Preview
@Composable
private fun CalendarScreenPreview() {
    CalendarScreen()
}