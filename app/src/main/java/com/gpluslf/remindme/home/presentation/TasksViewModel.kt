package com.gpluslf.remindme.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gpluslf.remindme.core.domain.TaskDataSource
import com.gpluslf.remindme.core.presentation.model.TaskUi
import com.gpluslf.remindme.core.presentation.model.toTask
import com.gpluslf.remindme.core.presentation.model.toTaskUi
import com.gpluslf.remindme.home.presentation.model.ListScreenAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class TasksState(
    val tasks: List<TaskUi>,
)

class TasksViewModel(
    private val userId: Long,
    private val listTitle: String,
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
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.getAllTasksByList(listTitle, userId).collect { flow ->
                    _state.update { state ->
                        state.copy(
                            tasks = flow.map { it.toTaskUi() }
                        )
                    }
                }
            }
        }
    }

    fun onAction(action: ListScreenAction) {
        when (action) {
            is ListScreenAction.ToggleTask -> {
                viewModelScope.launch {
                    repository.upsertTask(
                        action.task.copy(
                            isDone = !action.task.isDone
                        ).toTask()
                    )
                }
            }
        }
    }
}