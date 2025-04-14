package com.gpluslf.remindme.core.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationDTO(
    val id: Long,
    val title: String,
    val body: String,
    @SerialName("user_id")
    val userId: Long,
    @SerialName("sender_user_id")
    val senderUserId: Long? = null,
    @SerialName("send_time")
    val sendTime: Long,
    @SerialName("is_read")
    val isRead: Boolean,
    @SerialName("task_id")
    val taskId: Long,
    @SerialName("task_title")
    val taskTitle: String,
    @SerialName("task_list_id")
    val taskListId: Long,
    @SerialName("achievement_id")
    val achievementId: Long
)