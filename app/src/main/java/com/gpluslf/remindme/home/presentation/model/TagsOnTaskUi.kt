package com.gpluslf.remindme.home.presentation.model

import com.gpluslf.remindme.core.data.database.entities.TagsOnTaskEntity
import com.gpluslf.remindme.core.domain.TagsOnTask

data class TagsOnTaskUi(
    val taskTitle: String,
    val taskListTitle: String,
    val taskUserId: Long,
    val tagId: Long,
)

fun TagsOnTask.toTagsOnTaskUi() = TagsOnTaskUi(
    taskTitle,
    taskListTitle,
    taskUserId,
    tagId,
)
