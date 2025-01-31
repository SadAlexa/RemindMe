package com.gpluslf.remindme.core.presentation.model

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.gpluslf.remindme.core.domain.AlarmScheduler
import com.gpluslf.remindme.core.domain.Notification

class NotificationAlarmScheduler(
    private val context: Context
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun createPendingIntent(notificationItem: Notification): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("id", notificationItem.id)
            putExtra("title", notificationItem.title)
            putExtra("body", notificationItem.body)
            putExtra("userId", notificationItem.userId)
            putExtra("senderUserId", notificationItem.senderUserId)
            putExtra("sendTime", notificationItem.sendTime.time)
            putExtra("isRead", notificationItem.isRead)
            putExtra("taskTitle", notificationItem.taskTitle)
            putExtra("taskListTitle", notificationItem.taskListTitle)
            putExtra("achievementId", notificationItem.achievementId)
        }
        return PendingIntent.getBroadcast(
            context,
            notificationItem.id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    override fun schedule(notificationItem: Notification) {
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            notificationItem.sendTime.time,
            createPendingIntent(notificationItem)
        )
    }

    override fun cancel(notificationItem: Notification) {
        alarmManager.cancel(
            createPendingIntent(notificationItem)
        )
    }
}