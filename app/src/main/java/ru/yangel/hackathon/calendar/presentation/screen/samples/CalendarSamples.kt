package ru.yangel.hackathon.calendar.presentation.screen.samples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CalendarSamples(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        BirthdayCalendarDaySample()
        TodayCalendarDaySample()
        RegularCalendarDaySample()
    }
}