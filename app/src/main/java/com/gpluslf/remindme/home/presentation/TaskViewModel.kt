package com.gpluslf.remindme.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gpluslf.remindme.core.domain.TaskDataSource
import com.gpluslf.remindme.home.presentation.model.TaskUi
import com.gpluslf.remindme.home.presentation.model.toTaskUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TasksState(
    val tasks: List<TaskUi>,
    val listTitle: String = ""
)

class TasksViewModel(
    private val repository: TaskDataSource
) : ViewModel() {
    private val _state = MutableStateFlow(TasksState(emptyList()))
    val state = _state.onStart {
        loadData()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = TasksState(emptyList())
    )

    private fun loadData() {
        repository.getAllTasksByList("title", 1 /*TODO*/).map {flow ->
            _state.update { state ->
                state.copy(tasks = flow.map { it.toTaskUi() })
            }
        }
    }

    fun upsertTask(task: TaskUi) = viewModelScope.launch {
        // TODO
    }

    fun deleteTask(task: TaskUi) = viewModelScope.launch {
        // TODO
    }
}