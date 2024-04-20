package ru.yangel.hackathon.calendar.presentation.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import kotlinx.coroutines.launch
import ru.yangel.hackathon.R
import ru.yangel.hackathon.calendar.presentation.utils.displayText
import ru.yangel.hackathon.calendar.presentation.utils.noRippleInteractionSource
import ru.yangel.hackathon.calendar.presentation.utils.rememberFirstMostVisibleMonth
import ru.yangel.hackathon.ui.theme.CodGray
import ru.yangel.hackathon.ui.theme.SuvaGray
import ru.yangel.hackathon.ui.theme.Type20
import ru.yangel.hackathon.ui.theme.White
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun Calendar(
    birthdays: List<CalendarDay>,
    onBirthdayClick: (CalendarDay) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentMonth = remember { YearMonth.now() }
    val today = remember { LocalDate.now() }
    val startMonth = remember { currentMonth }
    val endMonth = remember { currentMonth.plusMonths(12) }
    val daysOfWeek = remember { daysOfWeek(firstDayOfWeek = DayOfWeek.MONDAY) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(White)
    ) {
        val state = rememberCalendarState(
            startMonth = startMonth,
            endMonth = endMonth,
            firstVisibleMonth = currentMonth,
            firstDayOfWeek = daysOfWeek.first()
        )
        val coroutineScope = rememberCoroutineScope()
        val visibleMonth = rememberFirstMostVisibleMonth(state, viewportPercent = 90f)

        CalendarTitle(
            currentMonth = visibleMonth.yearMonth,
            isPreviousEnabled = state.firstVisibleMonth.yearMonth.isAfter(startMonth),
            isNextEnabled = state.firstVisibleMonth.yearMonth.isBefore(endMonth),
            goToPrevious = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.previousMonth)
                }
            },
            goToNext = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.nextMonth)
                }
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        HorizontalCalendar(
            state = state,
            dayContent = { day ->
                val isBirthday = birthdays.contains(day)
                CalendarDay(
                    day = day,
                    isBirthday = isBirthday,
                    isToday = day.date.isEqual(today),
                    onClick = {
                        if (isBirthday) {
                            onBirthdayClick(it)
                        }
                    }
                )
            },
            monthHeader = {
                CalendarMonthHeader(daysOfWeek = daysOfWeek)
            }
        )
    }
}

@Composable
fun CalendarTitle(
    modifier: Modifier = Modifier,
    currentMonth: YearMonth,
    isPreviousEnabled: Boolean,
    isNextEnabled: Boolean,
    goToPrevious: () -> Unit,
    goToNext: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                if (isPreviousEnabled) {
                    goToPrevious()
                }
            },
            interactionSource = if (!isPreviousEnabled) {
                noRippleInteractionSource
            } else {
                remember { MutableInteractionSource() }
            }
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.arrow_back),
                tint = if (isPreviousEnabled) CodGray else SuvaGray,
                contentDescription = null
            )
        }

        Text(
            text = currentMonth.month.displayText(),
            style = Type20,
            color = CodGray
        )

        IconButton(
            onClick = {
                if (isNextEnabled) {
                    goToNext()
                }
            },
            interactionSource = if (!isNextEnabled) {
                noRippleInteractionSource
            } else {
                remember { MutableInteractionSource() }
            }
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_forward),
                tint = if (isNextEnabled) CodGray else SuvaGray,
                contentDescription = null
            )
        }
    }
}