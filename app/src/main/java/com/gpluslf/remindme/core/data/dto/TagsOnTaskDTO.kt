package com.gpluslf.remindme.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class TagsOnTaskDTO(
    val taskId: String,
    val taskListId: String,
    val tagId: String,
    val taskUserId: Long,
)