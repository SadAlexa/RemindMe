package com.gpluslf.remindme.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDTO(
    val id: String,
    val title: String,
    val userId: Long,
)