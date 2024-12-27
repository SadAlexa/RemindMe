package com.gpluslf.remindme.core.data.mappers

import androidx.room.TypeConverter
import com.gpluslf.remindme.core.data.database.entities.TaskEntity
import com.gpluslf.remindme.core.domain.Image
import com.gpluslf.remindme.core.domain.Task

fun TaskEntity.toTask() = Task(
    title,
    listTitle,
    userId,
    body,
    endTime?.toDate(),
    frequency?.toDate(),
    alert?.toDate(),
    if (image != null) Image(image) else null,
)

fun Task.toTaskEntity() = TaskEntity(
    title,
    listTitle,
    userId,
    body,
    endTime?.toLong(),
    frequency?.toLong(),
    alert?.toLong(),
    image?.bytes
)