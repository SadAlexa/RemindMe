package com.gpluslf.remindme.core.data.database.repository

import com.gpluslf.remindme.core.data.database.daos.AchievementDAOs
import com.gpluslf.remindme.core.data.database.entities.AchievementEntity

class AchievementRepository(private val achievementDAOs: AchievementDAOs) {

    fun getAllAchievements(userId: Long) = achievementDAOs.getAllAchievements(userId)

    suspend fun upsertAchievement(achievement: AchievementEntity) = achievementDAOs.upsertAchievement(achievement)

    suspend fun deleteAchievement(achievement: AchievementEntity) = achievementDAOs.deleteAchievement(achievement)
}