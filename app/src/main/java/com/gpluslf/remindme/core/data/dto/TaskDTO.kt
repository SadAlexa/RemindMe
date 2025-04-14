package com.gpluslf.remindme.core.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskDTO(
    val id: Long,
    val title: String,
    @SerialName("list_id")
    val listId: Long,
    @SerialName("user_id")
    val userId: Long,
    val body: String,
    val image: String? = null,
    @SerialName("end_time")
    val endTime: Long? = null,
    val frequency: Long? = null,
    val alert: Long? = null,
    @SerialName("is_done")
    val isDone: Boolean,
    val latitude: Double? = null,
    val longitude: Double? = null
)