package com.gpluslf.remindme.core.presentation.model

import android.net.Uri
import com.gpluslf.remindme.core.domain.Task
import java.util.Date

data class TaskUi(
    val title: String,
    val listTitle: String,
    val userId: Long,
    val body: String?,
    val endTime: Date?,
    val frequency: Date?,
    val alert: Date?,
    val image: Uri?,
    val isDone: Boolean,
    val latitude: Double?,
    val longitude: Double?,
    val tags : List<TagUi> = emptyList()
)

fun Task.toTaskUi() = TaskUi(
    title,
    listTitle,
    userId,
    body,
    endTime,
    frequency,
    alert,
    image,
    isDone,
    latitude,
    longitude,
    tags.map { it.toTagUi() }
)

