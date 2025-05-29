package com.gpluslf.remindme.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class NotificationDTO(
    val id: String,
    val title: String,
    val body: String,
    val userId: Long,
    val senderUserId: Long? = null,
    val sendTime: Long,
    val isRead: Boolean,
    val taskId: String? = null,
    val taskTitle: String? = null,
    val taskListId: String? = null,
    val achievementId: Long? = null
)