package ru.yangel.hackathon.calendar.presentation.state

sealed interface CalendarUiState {

    data object Initial : CalendarUiState

    data object Loading : CalendarUiState

    data object Error : CalendarUiState

    data class Content(val birthdays: List<Birthday>) : CalendarUiState

}