package com.gpluslf.remindme.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class TodoListDTO(
    val id: String,
    val title: String,
    val userId: Long,
    val body: String? = null,
    val image: String? = null,
    val isShared: Boolean = false,
    val sharedUserId: Long? = null,
    val categoryId: String? = null,
)