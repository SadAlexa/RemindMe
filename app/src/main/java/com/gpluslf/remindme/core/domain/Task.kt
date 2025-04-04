package com.gpluslf.remindme.core.domain

import android.net.Uri
import java.util.Date

data class Task(
    
    val id: Long,

    val title: String,

    val listId: Long,

    val userId: Long,

    val body: String? = null,

    val endTime: Date? = null,

    val frequency: Date? = null,

    val alert: Date? = null,

    val image: Uri? = null,

    val isDone: Boolean = false,

    val latitude: Double? = null,

    val longitude: Double? = null,

    val tags: List<Tag> = emptyList()
)

