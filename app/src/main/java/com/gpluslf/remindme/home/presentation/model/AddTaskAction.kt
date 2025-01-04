package com.gpluslf.remindme.home.presentation.model

import android.net.Uri
import java.util.Date

sealed interface AddTaskAction {
    data class UpdateTitle(val title: String) : AddTaskAction
    data class UpdateBody(val body: String) : AddTaskAction
    data class UpdateEndTime(val endTime: Long?) : AddTaskAction
    data class UpdateFrequency(val frequency: Date) : AddTaskAction
    data class UpdateAlert(val alert: Date) : AddTaskAction
    data class UpdateImage(val image: Uri) : AddTaskAction
    data class UpdateDone(val isDone: Boolean) : AddTaskAction
    data class UpdateLocation(val latitude: Double, val longitude: Double) : AddTaskAction
    data class UpdateTags(val tag: TagUi) : AddTaskAction
    data class ShowTimePicker(val isOpen: Boolean) : AddTaskAction
    data class ShowDatePicker(val isOpen: Boolean) : AddTaskAction
    data object RemoveEndTime : AddTaskAction
    data object SaveTask : AddTaskAction
}