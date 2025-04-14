package com.gpluslf.remindme.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDTO(
    val id: Long,
    val title: String,
    val userId: Long,
)