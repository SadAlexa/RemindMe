package com.gpluslf.remindme.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class TaskDTO(
    val id: String,
    val title: String,
    val listId: String,
    val userId: Long,
    val body: String? = null,
    val image: String? = null,
    val endTime: Long? = null,
    val frequency: Long? = null,
    val alert: Long? = null,
    val isDone: Boolean,
    val latitude: Double? = null,
    val longitude: Double? = null
)