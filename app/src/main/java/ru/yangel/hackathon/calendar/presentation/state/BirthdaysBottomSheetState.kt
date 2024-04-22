package ru.yangel.hackathon.calendar.presentation.state

data class BirthdaysBottomSheetState(
    val isShown: Boolean = false,
    val date: String? = null,
    val birthdays: List<Birthday> = emptyList()
)
