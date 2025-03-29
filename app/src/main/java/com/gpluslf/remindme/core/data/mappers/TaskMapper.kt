package com.gpluslf.remindme.core.data.mappers

import android.net.Uri
import com.gpluslf.remindme.core.data.database.entities.TagEntity
import com.gpluslf.remindme.core.data.database.entities.TaskEntity
import com.gpluslf.remindme.core.domain.Task

fun TaskEntity.toTask(tags: List<TagEntity>) = Task(
    id,
    title,
    listId,
    userId,
    body,
    endTime?.toDate(),
    frequency?.toDate(),
    alert?.toDate(),
    image?.let { Uri.parse(it) },
    isDone,
    latitude,
    longitude,
    tags.map { it.toTag() }
)

fun Task.toTaskEntity() = TaskEntity(
    id,
    title,
    listId,
    userId,
    body,
    endTime?.toLong(),
    frequency?.toLong(),
    alert?.toLong(),
    image?.toString(),
    isDone,
    latitude,
    longitude
)