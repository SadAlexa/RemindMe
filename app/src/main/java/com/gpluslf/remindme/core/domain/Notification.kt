package com.gpluslf.remindme.core.domain

import java.util.Date

data class Notification(

    val id: Long,

    val title: String,

    val body: String,

    val userId: Long,

    val senderUserId: Long? = null,

    val sendTime: Date,

    val isRead: Boolean = false,

    val taskId: Long? = null,

    val taskTitle: String? = null,

    val taskListId: Long? = null,

    val achievementId: Long? = null
)

