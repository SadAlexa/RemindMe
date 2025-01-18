package com.gpluslf.remindme.core.domain

import java.util.Date

data class Notification(

    val id: Long,

    val title: String,

    val body: String,

    val userId: Long,

    val senderUserId: Long? = null,

    val sendTime: Date,

    val isRead: Boolean,

    val taskTitle: String? = null,

    val taskListTitle: String? = null,

    val achievementId: Long? = null
)

