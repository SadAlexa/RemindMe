package com.gpluslf.remindme.updates.presentation.model

import android.net.Uri
import com.gpluslf.remindme.core.domain.Notification
import com.gpluslf.remindme.core.domain.TodoList
import com.gpluslf.remindme.home.presentation.model.CategoryUi
import com.gpluslf.remindme.home.presentation.model.TaskUi
import com.gpluslf.remindme.home.presentation.model.toCategoryUi
import java.util.Date

data class NotificationUi(
    val id: Long,

    val title: String,

    val body: String,

    val userId: Long,

    val senderUserId: Long?,

    val sendTime: Date,

    val isRead: Boolean,

    val taskTitle: String?,

    val taskListTitle: String?,

    val achievementTitle: String?
)

fun Notification.toNotificationUi() = NotificationUi(
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
