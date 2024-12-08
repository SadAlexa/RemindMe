package com.gpluslf.remindme.core.data.database.repository

import com.gpluslf.remindme.core.data.database.daos.NotificationDAOs
import com.gpluslf.remindme.core.data.database.entities.NotificationEntity

class NotificationRepository(private val notificationDAOs: NotificationDAOs) {

    fun getNotificationById(notificationId: Long) = notificationDAOs.getNotificationById(notificationId)

    fun getAllNotifications(userId: Long) = notificationDAOs.getAllNotifications(userId)

    suspend fun upsertNotification(notification: NotificationEntity) = notificationDAOs.upsertNotification(notification)

    suspend fun deleteNotification(notification: NotificationEntity) = notificationDAOs.deleteNotification(notification)
}