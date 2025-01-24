package com.gpluslf.remindme.profile.presentation.model

import com.gpluslf.remindme.core.domain.UserAchievement

data class PercentageUi(val value: Float, val formatted: String)

fun Float.toPercentageUi(): PercentageUi {
    val formatted = "${(this * 100).toInt()}%"
    return PercentageUi(this, formatted)
}

data class UserAchievementUi(

    val achievementId: Long,

    val achievementTitle: String,

    val achievementBody: String,

    val achievementNumber: Int,

    val userId: Long,

    val userNumber: Int,

    val isCompleted: Boolean,

    val percentage: PercentageUi
)

fun UserAchievement.toUserAchievementUi(): UserAchievementUi {
    return UserAchievementUi(
        achievementId,
        achievementTitle,
        achievementBody,
        achievementNumber,
        userId,
        userNumber,
        isCompleted,
        (userNumber.toFloat() / achievementNumber.toFloat()).toPercentageUi()
    )
}

