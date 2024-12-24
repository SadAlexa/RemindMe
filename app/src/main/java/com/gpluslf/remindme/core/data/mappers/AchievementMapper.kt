package com.gpluslf.remindme.core.data.mappers

import com.gpluslf.remindme.core.data.database.entities.AchievementEntity
import com.gpluslf.remindme.core.domain.Achievement

fun AchievementEntity.toAchievement() = Achievement(
    title,
    userId,
    body,
    isCompleted,
    percentage
)

fun Achievement.toEntity() = AchievementEntity(
    title,
    userId,
    body,
    isCompleted,
    percentage
)
