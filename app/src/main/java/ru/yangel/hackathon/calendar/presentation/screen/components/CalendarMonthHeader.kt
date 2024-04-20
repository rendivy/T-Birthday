package ru.yangel.hackathon.calendar.presentation.screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import ru.yangel.hackathon.calendar.presentation.utils.displayText
import ru.yangel.hackathon.ui.theme.Nevada
import ru.yangel.hackathon.ui.theme.Type15
import java.time.DayOfWeek

@Composable
fun CalendarMonthHeader(daysOfWeek: List<DayOfWeek>) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = Nevada,
                style = Type15,
                text = dayOfWeek.displayText(),
            )
        }
    }
}