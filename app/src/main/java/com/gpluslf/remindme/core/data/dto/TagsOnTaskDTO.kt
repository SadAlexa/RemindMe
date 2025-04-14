package com.gpluslf.remindme.core.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TagsOnTaskDTO(
    @SerialName("task_id")
    val taskId: Long,
    @SerialName("task_list_id")
    val taskListId: Long,
    @SerialName("tag_id")
    val tagId: Long,
    @SerialName("task_user_id")
    val taskUserId: Long,
)