package com.gpluslf.remindme.core.data.mappers

import com.gpluslf.remindme.core.data.database.entities.NotificationEntity
import com.gpluslf.remindme.core.domain.Notification

fun NotificationEntity.toNotification() = Notification(
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

fun Notification.toNotificationEntity() = NotificationEntity(
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

