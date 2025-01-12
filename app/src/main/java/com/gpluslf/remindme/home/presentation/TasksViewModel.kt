package com.gpluslf.remindme.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gpluslf.remindme.core.domain.Tag
import com.gpluslf.remindme.core.domain.TagDataSource
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
    val showDialog: Boolean = false,
    val tagTitle: String = ""
)

class TasksViewModel(
    private val userId: Long,
    private val listTitle: String,
    private val taskRepository: TaskDataSource,
    private val tagRepository: TagDataSource
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
                taskRepository.getAllTasksByList(listTitle, userId).collect { flow ->
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
                    taskRepository.upsertTask(
                        action.task.copy(
                            isDone = !action.task.isDone
                        ).toTask()
                    )
                }
            }

            is ListScreenAction.ShowDialog -> {
                _state.update { state -> state.copy(showDialog = action.showDialog) }
            }

            is ListScreenAction.UpdateTagTitle -> {
                _state.update { state -> state.copy(tagTitle = action.tagTitle) }
            }

            ListScreenAction.SaveTag -> {
                viewModelScope.launch {
                    tagRepository.upsertTag(
                        Tag(
                            id = 0,
                            title = state.value.tagTitle,
                            listTitle = listTitle,
                            userId
                        )
                    )
                }
                _state.update { state -> state.copy(showDialog = false, tagTitle = "") }
            }
        }
    }
}