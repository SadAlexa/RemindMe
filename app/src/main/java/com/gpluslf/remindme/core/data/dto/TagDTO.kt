package com.gpluslf.remindme.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class TagDTO(
    val id: String,
    val title: String,
    val listId: String,
    val userId: Long,
)