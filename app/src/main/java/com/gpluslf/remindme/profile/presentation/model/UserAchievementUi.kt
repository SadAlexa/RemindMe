package com.gpluslf.remindme.profile.presentation.model

import com.gpluslf.remindme.core.domain.UserAchievement

data class UserAchievementUi(

    val achievementId: Long,

    val achievementTitle: String,

    val achievementBody: String,

    val achievementNumber: Int,

    val userId: Long,

    val userNumber: Int,

    val isCompleted: Boolean,

    val percentage: Float
)

fun UserAchievement.toUserAchievementUi() = UserAchievementUi(
    achievementId,
    achievementTitle,
    achievementBody,
    achievementNumber,
    userId,
    userNumber,
    isCompleted,
    userNumber.toFloat() / achievementNumber.toFloat()
)