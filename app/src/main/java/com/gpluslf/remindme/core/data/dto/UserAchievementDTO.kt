package com.gpluslf.remindme.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserAchievementDTO(
    val achievementId: Long,
    val userId: Long,
    val isCompleted: Boolean,
    val isNotified: Boolean,
    val number: Int
)