package com.gpluslf.remindme.core.data.mappers

import com.gpluslf.remindme.core.data.database.entities.TagsOnTaskEntity
import com.gpluslf.remindme.core.domain.TagsOnTask

fun TagsOnTaskEntity.toTagsOnTask() = TagsOnTask(
    taskTitle,
    taskListTitle,
    taskUserId,
    tagId,
)

fun TagsOnTask.toTagsOnTaskEntity() = TagsOnTaskEntity(
    taskTitle,
    taskListTitle,
    taskUserId,
    tagId,
)
