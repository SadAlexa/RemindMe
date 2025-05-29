package com.gpluslf.remindme.updates.presentation.model

import com.gpluslf.remindme.core.domain.Notification
import java.util.Date

data class NotificationUi(
    val id: String,

    val title: String,

    val body: String,

    val userId: Long,

    val senderUserId: Long?,

    val sendTime: Date,

    val isRead: Boolean,

    val taskId: String?,

    val taskTitle: String?,

    val taskListId: String?,

    val achievementId: Long?
)

fun Notification.toNotificationUi() = NotificationUi(
    id,
    title,
    body,
    userId,
    senderUserId,
    sendTime,
    isRead,
    taskId,
    taskTitle,
    taskListId,
    achievementId
)

fun NotificationUi.toNotification() = Notification(
    id,
    title,
    body,
    userId,
    senderUserId,
    sendTime,
    isRead,
    taskId,
    taskTitle,
    taskListId,
    achievementId
)
