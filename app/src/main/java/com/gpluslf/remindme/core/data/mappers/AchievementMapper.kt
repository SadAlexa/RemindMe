package com.gpluslf.remindme.core.data.mappers

import com.gpluslf.remindme.core.data.database.entities.UserAchievementEntity
import com.gpluslf.remindme.core.data.database.views.UserAchievementView
import com.gpluslf.remindme.core.data.dto.UserAchievementDTO
import com.gpluslf.remindme.core.domain.UserAchievement

fun UserAchievementView.toUserAchievement() = UserAchievement(
    achievementId,
    achievementTitle,
    achievementBody,
    achievementNumber,
    userId,
    userNumber,
    isCompleted,
    isNotified
)

fun UserAchievement.toUserAchievementEntity() = UserAchievementEntity(
    achievementId,
    userId,
    isCompleted,
    isNotified,
    userNumber
)

fun UserAchievementDTO.toUserAchievementEntity() = UserAchievementEntity(
    achievementId,
    userId,
    isCompleted,
    isNotified,
    number
)

fun UserAchievementEntity.toUserAchievementDTO() = UserAchievementDTO(
    achievementId,
    userId,
    isCompleted,
    isNotified,
    number
)