package ru.yangel.hackathon.calendar.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.yangel.hackathon.calendar.data.repository.SubscriptionsRepository
import ru.yangel.hackathon.calendar.presentation.state.Birthday
import ru.yangel.hackathon.calendar.presentation.state.BirthdaysBottomSheetState
import ru.yangel.hackathon.calendar.presentation.state.CalendarUiState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

private const val PATTERN = "d MMMM, E"
private val dateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN, Locale("ru", "RU"))

class CalendarViewModel(
    private val subscriptionsRepository: SubscriptionsRepository
) : ViewModel() {

    private val _calendarUiState = MutableStateFlow<CalendarUiState>(CalendarUiState.Initial)
    val calendarUiState = _calendarUiState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _calendarUiState.update { CalendarUiState.Error }
        Log.e("CalendarViewModel", "error: could not retrieve subscriptions from database")
    }

    private val _sheetState = MutableStateFlow(BirthdaysBottomSheetState())
    val sheetState = _sheetState.asStateFlow()

    init {
        _calendarUiState.update { CalendarUiState.Loading }
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val startDate = LocalDate.now().withDayOfMonth(1)
            val endDate = startDate.plusYears(1).plusMonths(1)
            subscriptionsRepository.getLocalPersonalSubscriptions()
                .collect { subscriptions ->
                    val birthdays = mutableListOf<Birthday>()
                    subscriptions.forEach {
                        val thisYearBirthday = it.birthDate.withYear(startDate.year)
                        val nextYearBirthday = it.birthDate.withYear(startDate.year + 1)
                        if (thisYearBirthday in startDate..endDate) {
                            val birthday = Birthday(
                                id = it.id,
                                fullName = it.fullName,
                                photoUrl = it.photoUrl,
                                calendarDay = CalendarDay(
                                    date = thisYearBirthday,
                                    position = DayPosition.MonthDate
                                )
                            )
                            birthdays.add(birthday)
                        }
                        if (nextYearBirthday in startDate..endDate) {
                            val birthday = Birthday(
                                id = it.id,
                                fullName = it.fullName,
                                photoUrl = it.photoUrl,
                                calendarDay = CalendarDay(
                                    date = nextYearBirthday,
                                    position = DayPosition.MonthDate
                                )
                            )
                            birthdays.add(birthday)
                        }
                    }
                    _calendarUiState.update {
                        CalendarUiState.Content(birthdays)
                    }
                }
        }
    }

    fun onBirthdayClick(day: CalendarDay) {
        val state = _calendarUiState.value as? CalendarUiState.Content
        state?.let {
            val birthdays = state.birthdays.filter {
                it.calendarDay == day
            }
            val formattedDate = day.date.format(dateTimeFormatter)
            _sheetState.update {
                it.copy(
                    isShown = true,
                    date = formattedDate,
                    birthdays = birthdays
                )
            }
        }
    }

    fun onDismissBottomSheet() {
        _sheetState.update { BirthdaysBottomSheetState() }
    }

}