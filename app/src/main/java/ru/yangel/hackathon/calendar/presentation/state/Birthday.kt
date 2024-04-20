package ru.yangel.hackathon.calendar.presentation.state

import com.kizitonwose.calendar.core.CalendarDay

data class Birthday(
    val id: String,
    val fullName: String,
    val photoUrl: String,
    val calendarDay: CalendarDay
)
