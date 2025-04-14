package com.gpluslf.remindme.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val id: Long,
    val username: String,
    val email: String,
    val image: String? = null,
    val password: String,
    val salt: String,
)
