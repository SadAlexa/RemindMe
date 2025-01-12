package com.gpluslf.remindme.core.data.mappers

import com.gpluslf.remindme.core.data.database.entities.TagEntity
import com.gpluslf.remindme.core.data.database.entities.TagsOnTaskEntity
import com.gpluslf.remindme.core.domain.Tag

fun TagEntity.toTag() = Tag(
    id,
    title,
    listTitle,
    userId
)

fun Tag.toTagEntity() = TagEntity(
    id,
    title,
    listTitle,
    userId
)

fun Tag.toTagsOnTaskEntity(
    taskTitle: String,
    listTitle: String,
    userId: Long
) = TagsOnTaskEntity(
    tagId = id,
    taskTitle = taskTitle,
    taskUserId = userId,
    taskListTitle = listTitle
)
