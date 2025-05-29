package com.gpluslf.remindme.core.data.mappers

import com.gpluslf.remindme.core.data.database.entities.TagEntity
import com.gpluslf.remindme.core.data.database.entities.TagsOnTaskEntity
import com.gpluslf.remindme.core.data.dto.TagDTO
import com.gpluslf.remindme.core.data.dto.TagsOnTaskDTO
import com.gpluslf.remindme.core.domain.Tag

fun TagEntity.toTag() = Tag(
    id.toString(),
    title,
    listId.toString(),
    userId
)

fun Tag.toTagEntity() = TagEntity(
    id.toUUID(),
    title,
    listId.toUUID(),
    userId
)

fun Tag.toTagsOnTaskEntity(
    taskId: String,
    listId: String,
    userId: Long
) = TagsOnTaskEntity(
    taskId = taskId.toUUID(),
    taskListId = listId.toUUID(),
    taskUserId = userId,
    tagId = id.toUUID(),
)

fun TagDTO.toTagEntity() = TagEntity(
    id.toUUID(),
    title,
    listId.toUUID(),
    userId
)

fun TagsOnTaskDTO.toTagsOnTaskEntity() = TagsOnTaskEntity(
    taskId = taskId.toUUID(),
    taskListId = taskListId.toUUID(),
    taskUserId = taskUserId,
    tagId = tagId.toUUID(),
)

fun TagsOnTaskEntity.toTagsOnTaskDTO() = TagsOnTaskDTO(
    taskId = taskId.toString(),
    taskListId = taskListId.toString(),
    taskUserId = taskUserId,
    tagId = tagId.toString(),
)

fun TagEntity.toTagDTO() = TagDTO(
    id.toString(),
    title,
    listId.toString(),
    userId
)
