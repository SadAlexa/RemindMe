package com.gpluslf.remindme.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ValidateServerDTO(
    val remindMeServer: Boolean
)
