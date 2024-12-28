package com.gpluslf.remindme.core.data.mappers

import androidx.room.TypeConverter
import com.gpluslf.remindme.core.data.database.entities.TagEntity
import com.gpluslf.remindme.core.data.database.entities.TaskEntity
import com.gpluslf.remindme.core.domain.Image
import com.gpluslf.remindme.core.domain.Tag
import com.gpluslf.remindme.core.domain.Task

fun TaskEntity.toTask(tags: List<TagEntity>) = Task(
    title,
    listTitle,
    userId,
    body,
    endTime?.toDate(),
    frequency?.toDate(),
    alert?.toDate(),
    if (image != null) Image(image) else null,
    isDone,
    tags.map { it.toTag() }
)

fun Task.toTaskEntity() = TaskEntity(
    title,
    listTitle,
    userId,
    body,
    endTime?.toLong(),
    frequency?.toLong(),
    alert?.toLong(),
    image?.bytes,
    isDone
)