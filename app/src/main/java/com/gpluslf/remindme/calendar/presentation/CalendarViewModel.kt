package com.gpluslf.remindme.calendar.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gpluslf.remindme.calendar.presentation.model.CalendarAction
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
    val selectedDay: LocalDate = LocalDate.now(),
    val isCalendarOpen: Boolean = false
)

class CalendarViewModel(
    private val userId: Long,
    private val taskDataSource: TaskDataSource
) : ViewModel() {
    private val _state = MutableStateFlow(CalendarTaskState())
    val state = _state.onStart {
        getMonthTasks(
            _state.value.currentMonth.atDay(1),
            _state.value.currentMonth.atEndOfMonth()
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        CalendarTaskState()
    )

    private fun getMonthTasks(start: LocalDate, end: LocalDate) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val tasks =
                    taskDataSource.getAllTaskByYearMonth(start, end, userId).map { it.toTaskUi() }
                _state.update { state ->
                    state.copy(tasks = tasks)
                }
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
                if (action.month != _state.value.currentMonth) {
                    getMonthTasks(action.month.atDay(1), action.month.atEndOfMonth())
                    _state.update { state ->
                        state.copy(currentMonth = action.month)
                    }
                }
            }

            is CalendarAction.SelectMonth -> {
                _state.update { state ->
                    state.copy(currentMonth = action.month)
                }
            }

            is CalendarAction.SelectDay -> {
                _state.update { state ->
                    state.copy(selectedDay = action.day)
                }
            }
        }
    }
}