package com.gpluslf.remindme.core.data.mappers

import com.gpluslf.remindme.core.data.database.entities.NotificationEntity
import com.gpluslf.remindme.core.domain.Notification

fun NotificationEntity.toNotification() = Notification(
    id,
    title,
    body,
    userId,
    senderUserId,
    sendTime.toDate(),
    isRead,
    taskTitle,
    taskListTitle,
    achievementId
)

fun Notification.toNotificationEntity() = NotificationEntity(
    id,
    title,
    body,
    userId,
    senderUserId,
    sendTime.toLong(),
    isRead,
    taskTitle,
    taskListTitle,
    achievementId
)

