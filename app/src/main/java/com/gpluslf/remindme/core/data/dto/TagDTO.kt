package com.gpluslf.remindme.core.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TagDTO(
    val id: Long,
    val title: String,
    @SerialName("list_id")
    val listId: Long,
    @SerialName("user_id")
    val userId: Long,
)