package com.gpluslf.remindme.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gpluslf.remindme.core.domain.TagDataSource
import com.gpluslf.remindme.core.domain.Task
import com.gpluslf.remindme.core.domain.TaskDataSource
import com.gpluslf.remindme.core.presentation.model.toTag
import com.gpluslf.remindme.core.presentation.model.toTagUi
import com.gpluslf.remindme.home.presentation.model.AddTaskAction
import com.gpluslf.remindme.home.presentation.model.CreateTaskState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Date

class AddTaskViewModel(
    private val userId: Long,
    private val listTitle: String,
    private val taskTitle: String? = null,
    private val taskRepository: TaskDataSource,
    private val tagsRepository: TagDataSource
) : ViewModel() {
    private val _state = MutableStateFlow(CreateTaskState())
    val state = _state.onStart {
        loadData()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        CreateTaskState()
    )

    private fun loadData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (taskTitle != null) {
                    taskRepository.getTaskByTitle(taskTitle, listTitle, userId).collect { task ->
                        if (task != null) {
                            _state.update { state ->
                                state.copy(
                                    title = task.title,
                                    body = task.body,
                                    image = task.image,
                                    endTime = task.endTime,
                                    frequency = task.frequency,
                                    alert = task.alert,
                                    isDone = task.isDone,
                                    latitude = task.latitude,
                                    longitude = task.longitude,
                                    selectedTags = task.tags.map { it.toTagUi() }
                                )
                            }
                        }
                    }
                }
                tagsRepository.getAllTags(listTitle, userId).collect { list ->
                    _state.update { state ->
                        state.copy(tags = list.map { it.toTagUi() })
                    }
                }
            }
        }
    }

    private fun saveTask() {
        viewModelScope.launch {
            taskRepository.upsertTask(
                Task(
                    title = _state.value.title,
                    body = _state.value.body,
                    image = _state.value.image,
                    listTitle = listTitle,
                    userId = userId,
                    tags = state.value.selectedTags.map { it.toTag() },
                    endTime = _state.value.endTime,
                    frequency = _state.value.frequency,
                    alert = _state.value.alert,
                    isDone = _state.value.isDone,
                    latitude = _state.value.latitude,
                    longitude = _state.value.longitude
                )
            )
        }
    }

    fun onAddTaskAction(action: AddTaskAction) {
        when (action) {
            is AddTaskAction.UpdateTitle -> {
                _state.update { state -> state.copy(title = action.title) }
            }

            is AddTaskAction.UpdateBody -> {
                _state.update { state -> state.copy(body = action.body) }
            }

            is AddTaskAction.UpdateImage -> {
                _state.update { state -> state.copy(image = action.image) }
            }

            is AddTaskAction.UpdateTags -> {
                _state.update { state ->
                    state.copy(
                        selectedTags =
                        if (_state.value.selectedTags.contains(action.tag))
                            _state.value.selectedTags - action.tag
                        else
                            _state.value.selectedTags + action.tag
                    )
                }
            }

            is AddTaskAction.ShowDatePicker -> {
                _state.update { state ->
                    state.copy(isDatePickerOpen = action.isOpen)
                }
            }

            is AddTaskAction.ShowTimePicker -> {
                _state.update { state ->
                    state.copy(isTimePickerOpen = action.isOpen)
                }
            }

            is AddTaskAction.UpdateAlert -> {
                _state.update { state ->
                    state.copy(alert = action.alert)
                }
            }

            is AddTaskAction.UpdateDone -> {
                _state.update { state ->
                    state.copy(isDone = action.isDone)
                }
            }

            is AddTaskAction.UpdateEndTime -> {
                val time = action.endTime
                val calendar = Calendar.getInstance()
                calendar.time = _state.value.endTime ?: Date()

                calendar.set(Calendar.HOUR_OF_DAY, time.first)
                calendar.set(Calendar.MINUTE, time.second)

                _state.update { state ->
                    state.copy(endTime = calendar.time)
                }
            }

            is AddTaskAction.UpdateEndDate -> {
                action.endDate ?: return

                val calendar = Calendar.getInstance()
                calendar.time = _state.value.endTime ?: Date()

                val hour = calendar.get(Calendar.HOUR_OF_DAY)
                val minute = calendar.get(Calendar.MINUTE)

                calendar.time = action.endDate

                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)

                _state.update { state ->
                    state.copy(endTime = calendar.time)
                }
            }

            is AddTaskAction.UpdateFrequency -> {
                // TODO()
            }

            is AddTaskAction.UpdateLocation -> {
                // TODO()
            }

            AddTaskAction.RemoveEndTime -> {
                _state.update { state ->
                    state.copy(endTime = null)
                }
            }

            AddTaskAction.SaveTask -> saveTask()
        }
    }
}