package com.gpluslf.remindme.core.data.mappers

import android.net.Uri
import com.gpluslf.remindme.core.data.database.entities.TagEntity
import com.gpluslf.remindme.core.data.database.entities.TaskEntity
import com.gpluslf.remindme.core.data.dto.TaskDTO
import com.gpluslf.remindme.core.domain.Task

fun TaskEntity.toTask(tags: List<TagEntity>) = Task(
    id.toString(),
    title,
    listId.toString(),
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
    id.toUUID(),
    title,
    listId.toUUID(),
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

fun TaskDTO.toTaskEntity() = TaskEntity(
    id.toUUID(),
    title,
    listId.toUUID(),
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

fun TaskEntity.toTaskDTO() = TaskDTO(
    id.toString(),
    title,
    listId.toString(),
    userId,
    body,
    image,
    endTime,
    frequency,
    alert,
    isDone,
    latitude,
    longitude,
)