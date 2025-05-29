package com.gpluslf.remindme.core.presentation.model

import android.net.Uri
import com.gpluslf.remindme.core.domain.Coordinates
import com.gpluslf.remindme.core.domain.Task
import java.util.Date

data class TaskUi(
    val id: String,
    val title: String,
    val listId: String,
    val userId: Long,
    val body: String?,
    val endTime: Date?,
    val frequency: Date?,
    val alert: Date?,
    val image: Uri?,
    val isDone: Boolean,
    val coordinates: Coordinates?,
    val tags: List<TagUi> = emptyList()
)

fun Task.toTaskUi() = TaskUi(
    id,
    title,
    listId,
    userId,
    body,
    endTime,
    frequency,
    alert,
    image,
    isDone,
    coordinates = if (latitude == null || longitude == null) null else Coordinates(
        latitude,
        longitude
    ),
    tags.map { it.toTagUi() }
)

fun TaskUi.toTask() = Task(
    id,
    title,
    listId,
    userId,
    body,
    endTime,
    frequency,
    alert,
    image,
    isDone,
    coordinates?.latitude,
    coordinates?.longitude,
    tags.map { it.toTag() }
)

