package com.gpluslf.remindme.core.data.mappers

import com.gpluslf.remindme.core.data.database.entities.TagEntity
import com.gpluslf.remindme.core.data.database.entities.TagsOnTaskEntity
import com.gpluslf.remindme.core.domain.Tag

fun TagEntity.toTag() = Tag(
    id,
    title,
    listId,
    userId
)

fun Tag.toTagEntity() = TagEntity(
    id,
    title,
    listId,
    userId
)

fun Tag.toTagsOnTaskEntity(
    taskId: Long,
    listId: Long,
    userId: Long
) = TagsOnTaskEntity(
    taskId = taskId,
    taskListId = listId,
    taskUserId = userId,
    tagId = id,
)
