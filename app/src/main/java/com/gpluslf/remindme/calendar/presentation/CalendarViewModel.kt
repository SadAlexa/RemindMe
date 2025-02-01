package com.gpluslf.remindme.calendar.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gpluslf.remindme.calendar.presentation.model.CalendarAction
import com.gpluslf.remindme.core.data.mappers.toLong
import com.gpluslf.remindme.core.domain.TaskDataSource
import com.gpluslf.remindme.core.presentation.model.TaskUi
import com.gpluslf.remindme.core.presentation.model.toTaskUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.YearMonth

data class CalendarTaskState(
    val tasks: List<TaskUi> = emptyList(),
    val currentMonth: YearMonth = YearMonth.now(),
    val selectedDay: LocalDate = LocalDate.now().atStartOfDay().toLocalDate(),
    val isCalendarOpen: Boolean = false
)

class CalendarViewModel(
    private val userId: Long,
    private val taskDataSource: TaskDataSource
) : ViewModel() {
    private var monthTasks = emptyList<TaskUi>()
    private val _state = MutableStateFlow(CalendarTaskState())
    val state = _state.onStart {
        getMonthTasks(
            _state.value.currentMonth
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        CalendarTaskState()
    )

    private fun getMonthTasks(month: YearMonth) {

        val start = month.atDay(1)
        val end = month.atEndOfMonth().plusDays(1)

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                monthTasks =
                    taskDataSource.getAllTaskByYearMonth(start, end, userId).map { it.toTaskUi() }
                selectDay(_state.value.selectedDay)
            }
        }
    }

    fun onAction(action: CalendarAction) {
        when (action) {
            CalendarAction.ToggleCalendar -> {
                _state.update { state ->
                    state.copy(isCalendarOpen = !state.isCalendarOpen)
                }
            }

            is CalendarAction.SetCurrentMonth -> {
                setCurrentMonth(action.month)
            }

            is CalendarAction.SelectDay -> {
                selectDay(action.day)
            }
        }
    }

    private fun setCurrentMonth(month: YearMonth) {
        getMonthTasks(month)
        _state.update { state ->
            state.copy(currentMonth = month)
        }
    }

    private fun selectDay(date: LocalDate) {
        val startOfDay = date.atStartOfDay().toLocalDate().toLong()
        val endOfDay = date.atStartOfDay().plusDays(1).toLocalDate().toLong()
        val tasks =
            monthTasks.filter { it.endTime != null && it.endTime.toLong() in startOfDay..<endOfDay }
        _state.update { state ->
            state.copy(
                tasks = tasks,
                selectedDay = date
            )
        }
    }

    fun hasEvents(date: LocalDate): Boolean {
        return monthTasks.any {
            it.endTime != null && it.endTime.toLong() in date.atStartOfDay().toLocalDate()
                .toLong()..<date.atStartOfDay().plusDays(1).toLocalDate().toLong()
        }
    }
}