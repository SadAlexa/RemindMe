package com.gpluslf.remindme.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class TokenDTO(
    val accessToken: String,
)
