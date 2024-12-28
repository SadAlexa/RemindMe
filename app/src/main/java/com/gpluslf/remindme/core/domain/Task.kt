package com.gpluslf.remindme.core.domain

import java.util.Date

data class Task(
    val title: String,

    val listTitle: String,

    val userId: Long,

    val body: String? = null,

    val endTime: Date? = null,

    val frequency: Date? = null,

    val alert: Date? = null,

    val image: Image? = null,

    val isDone: Boolean = false,

    val tags: List<Tag> = emptyList()
)

