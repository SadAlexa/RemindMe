package com.gpluslf.remindme.core.domain

import kotlinx.coroutines.flow.Flow


interface NotificationDataSource {

    fun getNotificationById(notificationId: Long): Notification

    fun getAllNotifications(userId: Long): Flow<List<Notification>>
    
    suspend fun upsertNotification(notification: Notification)

    suspend fun deleteNotification(notification: Notification)
}