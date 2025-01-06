package com.gpluslf.remindme.calendar.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gpluslf.remindme.core.presentation.model.TaskUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

data class CalendarTaskState(
    val tasks: List<TaskUi> = emptyList()
)

class CalendarViewModel : ViewModel() {
    private val _state = MutableStateFlow(CalendarTaskState())
    val state = _state.onStart {
        /* TODO: load tasks */
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        CalendarTaskState()
    )
}