package com.gpluslf.remindme.core.domain

data class Notification(

    val id: Long,

    val title: String,

    val body: String,

    val userId: Long,

    val senderUserId: Long?,

    val sendTime: Long,

    val isRead: Boolean,

    val taskTitle: String?,

    val taskListTitle: String?,

    val achievementTitle: String?
)

