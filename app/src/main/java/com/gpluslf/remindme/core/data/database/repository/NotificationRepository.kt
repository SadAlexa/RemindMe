package com.gpluslf.remindme.core.data.database.repository

import com.gpluslf.remindme.core.data.database.daos.NotificationDAOs
import com.gpluslf.remindme.core.data.database.entities.NotificationEntity
import com.gpluslf.remindme.core.data.mappers.toNotification
import com.gpluslf.remindme.core.data.mappers.toNotificationEntity
import com.gpluslf.remindme.core.domain.Notification
import com.gpluslf.remindme.core.domain.NotificationDataSource
import kotlinx.coroutines.flow.map

class NotificationRepository(private val notificationDAOs: NotificationDAOs): NotificationDataSource {

    override fun getNotificationById(notificationId: Long) = notificationDAOs.getNotificationById(notificationId).map { notification -> notification.toNotification() }

    override fun getAllNotifications(userId: Long) = notificationDAOs.getAllNotifications(userId).map { flow -> flow.map { it.toNotification() } }

    override suspend fun upsertNotification(notification: Notification) = notificationDAOs.upsertNotification(notification.toNotificationEntity())

    override suspend fun deleteNotification(notification: Notification) = notificationDAOs.deleteNotification(notification.toNotificationEntity())
}