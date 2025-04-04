package com.gpluslf.remindme.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gpluslf.remindme.core.domain.Tag
import com.gpluslf.remindme.core.domain.TagDataSource
import com.gpluslf.remindme.core.domain.TaskDataSource
import com.gpluslf.remindme.core.presentation.model.TagUi
import com.gpluslf.remindme.core.presentation.model.TaskUi
import com.gpluslf.remindme.core.presentation.model.toTag
import com.gpluslf.remindme.core.presentation.model.toTagUi
import com.gpluslf.remindme.core.presentation.model.toTask
import com.gpluslf.remindme.core.presentation.model.toTaskUi
import com.gpluslf.remindme.home.presentation.model.ListScreenAction
import com.gpluslf.remindme.home.presentation.model.TagEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class TasksState(
    val tasks: List<TaskUi>,
    val showDialog: Boolean = false,
    val tagTitle: String = "",
    val selectedTag: TagUi? = null,
    val selectedEditTag: TagUi? = null,
    val tags: List<TagUi> = emptyList()
)

class TasksViewModel(
    private val userId: Long,
    private val listId: Long,
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

    private var allTasks: List<TaskUi> = emptyList()

    private val _events = Channel<TagEvent>()
    val events = _events.receiveAsFlow()

    private fun loadData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                combine(
                    taskRepository.getAllTasksByList(listId, userId),
                    tagRepository.getAllTags(listId, userId)
                ) { tasks, tags ->
                    Pair(
                        tasks.map { it.toTaskUi() },
                        tags.map { it.toTagUi() }
                    )
                }.collect { (tasks, tags) ->
                    allTasks = tasks
                    _state.update { state ->
                        state.copy(
                            tags = tags,
                            tasks = filterByTag(state.selectedTag)
                        )
                    }
                }
            }
        }
    }

    private fun filterByTag(tag: TagUi? = null): List<TaskUi> {
        return allTasks.filter { task ->
            tag == null || task.tags.map { it.id }.contains(tag.id)
        }
    }

    fun onAction(action: ListScreenAction) {
        when (action) {
            is ListScreenAction.ToggleTask -> {
                viewModelScope.launch {
                    taskRepository.upsertTask(
                        action.task.copy(
                            isDone = !action.task.isDone,
                        ).toTask()
                    )
                }
            }

            is ListScreenAction.ShowDialog -> {
                _state.update { state ->
                    state.copy(
                        showDialog = action.showDialog,
                        selectedEditTag = action.tag,
                        tagTitle = action.tag?.title ?: ""
                    )
                }
            }

            is ListScreenAction.UpdateTagTitle -> {
                _state.update { state -> state.copy(tagTitle = action.tagTitle) }
            }

            is ListScreenAction.SelectTag -> {
                _state.update { state ->
                    state.copy(
                        selectedTag = action.tag,
                        tasks = filterByTag(action.tag)
                    )
                }
            }

            ListScreenAction.SaveTag -> {
                viewModelScope.launch {
                    tagRepository.upsertTag(
                        Tag(
                            id = state.value.selectedEditTag?.id ?: 0,
                            title = state.value.tagTitle,
                            listId = listId,
                            userId
                        )
                    )
                    _events.send(TagEvent.TagCreated)
                }
                _state.update { state ->
                    state.copy(
                        showDialog = false,
                        tagTitle = "",
                        selectedEditTag = null
                    )
                }
            }

            is ListScreenAction.DeleteTask -> {
                viewModelScope.launch {
                    taskRepository.deleteTask(action.task.toTask())
                }
            }

            is ListScreenAction.EditTask -> {
                viewModelScope.launch {
                    taskRepository.upsertTask(
                        action.task.toTask()
                    )
                }
            }

            is ListScreenAction.DeleteTag -> {
                viewModelScope.launch {
                    tagRepository.deleteTag(action.tag.toTag())
                }
            }

            is ListScreenAction.EditTag -> {
                viewModelScope.launch {
                    tagRepository.upsertTag(
                        action.tag.toTag()
                    )
                }
            }
        }
    }
}