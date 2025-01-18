package com.gpluslf.remindme.core.data.mappers

import com.gpluslf.remindme.core.data.database.views.UserAchievementView
import com.gpluslf.remindme.core.domain.UserAchievement

fun UserAchievementView.toUserAchievement() = UserAchievement(
    achievementId,
    achievementTitle,
    achievementBody,
    achievementNumber,
    userId,
    userNumber,
    isCompleted
)

fun UserAchievement.toUserAchievementView() = UserAchievementView(
    achievementId,
    achievementTitle,
    achievementBody,
    achievementNumber,
    userId,
    userNumber,
    isCompleted
)