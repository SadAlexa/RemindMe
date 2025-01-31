package com.gpluslf.remindme.core.presentation.model

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import com.gpluslf.remindme.R

class RunnerNotifier(
    private val intent: PendingIntent?,
    private val title: String,
    private val message: String,
    private val context: Context,
    notificationManager: NotificationManager
) : Notifier(notificationManager) {
    override val notificationChannelId: String = "notification_channel"
    override val notificationChannelName: String = "Notifications"
    override val notificationId: Int = "$title$message".hashCode()

    override fun buildNotification(): Notification {
        return NotificationCompat.Builder(context, notificationChannelId)
            .setContentIntent(intent)
            .setContentTitle(getNotificationTitle())
            .setContentText(getNotificationMessage())
            .setSmallIcon(R.drawable.remindmeicon)
            .setAutoCancel(true)
            .build()
    }

    override fun getNotificationTitle(): String {
        return title
    }

    override fun getNotificationMessage(): String {
        return message
    }
}
