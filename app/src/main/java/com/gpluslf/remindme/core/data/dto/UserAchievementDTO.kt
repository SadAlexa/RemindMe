package com.gpluslf.remindme.core.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserAchievementDTO(
    @SerialName("achievement_id")
    val achievementId: Long,
    @SerialName("user_id")
    val userId: Long,
    @SerialName("is_completed")
    val isCompleted: Boolean,
    @SerialName("is_notified")
    val isNotified: Boolean,
    val number: Int
)