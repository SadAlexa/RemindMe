package com.gpluslf.remindme.core.domain

import android.app.PendingIntent

interface AlarmScheduler {

    fun createPendingIntent(notificationItem: Notification): PendingIntent

    fun schedule(notificationItem: Notification)

    fun cancel(notificationItem: Notification)
}