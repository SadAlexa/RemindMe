package com.gpluslf.remindme.updates.presentation.model

import com.gpluslf.remindme.core.domain.Notification
import java.util.Date

data class NotificationUi(
    val id: Long,

    val title: String,

    val body: String,

    val userId: Long,

    val senderUserId: Long?,

    val sendTime: Date,

    val isRead: Boolean,

    val taskTitle: String?,

    val taskListTitle: String?,

    val achievementTitle: String?
)

fun Notification.toNotificationUi() = NotificationUi(
    id,
    title,
    body,
    userId,
    senderUserId,
    sendTime,
    isRead,
    taskTitle,
    taskListTitle,
    achievementTitle
)
