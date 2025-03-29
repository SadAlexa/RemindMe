package com.gpluslf.remindme.core.presentation.model

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.TaskStackBuilder
import com.gpluslf.remindme.MainActivity
import com.gpluslf.remindme.core.data.mappers.toDate
import com.gpluslf.remindme.core.domain.Notification
import com.gpluslf.remindme.core.domain.NotificationDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val koin = object : KoinComponent {
            val k = getKoin()
        }.k
        val notificationDataSource = koin.get<NotificationDataSource>()
        val achievementId = intent.getLongExtra("achievementId", 0)
        val listId = intent.getLongExtra("taskListId", 0)
        val taskId = intent.getLongExtra("taskId", 0)
        val notification = Notification(
            intent.getLongExtra("id", 0),
            intent.getStringExtra("title") ?: "title default",
            intent.getStringExtra("body") ?: "body default",
            intent.getLongExtra("userId", 0),
            null,
            intent.getLongExtra("sendTime", 0).toDate(),
            intent.getBooleanExtra("isRead", false),
            if (taskId != 0L) taskId else null,
            intent.getStringExtra("taskTitle"),
            if (listId != 0L) listId else null,
            if (achievementId != 0L) achievementId else null
        )

        runBlocking {
            launch {
                withContext(Dispatchers.IO) {
                    notificationDataSource.upsertNotification(
                        notification
                    )

                    val notificationManager =
                        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    val resultIntent = Intent(context, MainActivity::class.java).apply {
                        putExtra(
                            "goToUpdates",
                            true
                        )
                    }
                    val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
                        addNextIntentWithParentStack(resultIntent)
                        getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                        )
                    }
                    val runnerNotifier =
                        RunnerNotifier(
                            resultPendingIntent,
                            notification.title,
                            notification.body,
                            context,
                            notificationManager
                        )
                    runnerNotifier.showNotification()
                }
            }
        }
    }
}
