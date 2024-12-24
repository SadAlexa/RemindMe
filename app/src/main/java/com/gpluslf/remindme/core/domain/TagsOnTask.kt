package com.gpluslf.remindme.core.domain

data class TagsOnTask(
    val taskTitle: String,

    val taskListTitle: String,

    val taskUserId: Long,

    val tagId: Long,
)
