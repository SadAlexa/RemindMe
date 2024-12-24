package com.gpluslf.remindme.core.data.mappers

import com.gpluslf.remindme.core.data.database.entities.TaskEntity
import com.gpluslf.remindme.core.domain.Image
import com.gpluslf.remindme.core.domain.Task

fun TaskEntity.toTask() = Task(
    title,
    listTitle,
    userId,
    body,
    endTime,
    frequency,
    alert,
    if (image != null) Image(image) else null,
)

fun Task.toTaskEntity() = TaskEntity(
    title,
    listTitle,
    userId,
    body,
    endTime,
    frequency,
    alert,
    image?.bytes
)

