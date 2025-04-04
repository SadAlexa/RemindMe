package com.gpluslf.remindme.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gpluslf.remindme.core.domain.AlarmScheduler
import com.gpluslf.remindme.core.domain.Coordinates
import com.gpluslf.remindme.core.domain.Notification
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
    private val listId: Long,
    private val taskId: Long? = null,
    private val taskRepository: TaskDataSource,
    private val tagsRepository: TagDataSource,
    private val alarmScheduler: AlarmScheduler
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
                if (taskId != null) {
                    launch {
                        taskRepository.getTaskById(taskId)
                            .collect { task ->
                                if (task != null) {
                                    _state.update { state ->
                                        state.copy(
                                            id = task.id,
                                            title = task.title,
                                            body = task.body,
                                            image = task.image,
                                            endTime = task.endTime,
                                            frequency = task.frequency,
                                            alert = task.alert,
                                            isDone = task.isDone,
                                            coordinates = if (task.latitude == null || task.longitude == null) null else Coordinates(
                                                task.latitude,
                                                task.longitude
                                            ),
                                            selectedTags = task.tags.map { it.toTagUi() }
                                        )
                                    }
                                }
                            }
                    }
                }
                tagsRepository.getAllTags(listId, userId).collect { list ->
                    _state.update { state ->
                        state.copy(tags = list.map { it.toTagUi() })
                    }
                }
            }
        }
    }

    private fun saveTask() {
        val currentState = state.value
        val task = Task(
            id = currentState.id,
            userId = userId,
            listId = listId,
            title = currentState.title,
            body = currentState.body,
            image = currentState.image,
            tags = currentState.selectedTags.map { it.toTag() },
            endTime = currentState.endTime,
            frequency = currentState.frequency,
            alert = currentState.alert,
            isDone = currentState.isDone,
            latitude = currentState.coordinates?.latitude,
            longitude = currentState.coordinates?.longitude
        )
        viewModelScope.launch {
            taskRepository.upsertTask(task)
        }


        if (currentState.endTime != null) {
            val scheduleId = "$userId/$listId/${currentState.title}".hashCode()
                .toLong() + currentState.endTime.time

            val notificationItem = Notification(
                id = scheduleId,
                sendTime = currentState.endTime,
                taskListId = listId,
                userId = userId,
                taskId = currentState.id,
                taskTitle = currentState.title,
                body = "it's time to ${currentState.title}",
                title = "REMINDER!",
            )
            alarmScheduler.cancel(notificationItem)
            alarmScheduler.schedule(
                notificationItem
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
                val currentTags = state.value.selectedTags
                _state.update { state ->
                    state.copy(
                        selectedTags = if (currentTags.contains(action.tag))
                            currentTags - action.tag
                        else
                            currentTags + action.tag
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

            is AddTaskAction.ShowMap -> {
                _state.update { state ->
                    state.copy(isMapOpen = action.isOpen)
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
                _state.update { state ->
                    state.copy(
                        coordinates = action.coordinates,
                        isMapOpen = false
                    )
                }
            }

            AddTaskAction.RemoveEndTime -> {
                _state.update { state ->
                    state.copy(endTime = null)
                }
            }

            AddTaskAction.SaveTask -> {
                saveTask()
            }

            is AddTaskAction.ShowImagePicker -> {
                _state.update { state -> state.copy(isImagePickerVisible = action.isOpen) }
            }
        }
    }
}