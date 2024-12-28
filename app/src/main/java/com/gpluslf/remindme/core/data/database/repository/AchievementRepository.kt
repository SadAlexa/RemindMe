package com.gpluslf.remindme.core.data.database.repository

import com.gpluslf.remindme.core.data.database.daos.AchievementDAOs
import com.gpluslf.remindme.core.data.database.entities.AchievementEntity
import com.gpluslf.remindme.core.data.mappers.toAchievement
import com.gpluslf.remindme.core.data.mappers.toEntity
import com.gpluslf.remindme.core.domain.Achievement
import com.gpluslf.remindme.core.domain.AchievementDataSource
import kotlinx.coroutines.flow.map

class AchievementRepository(private val achievementDAOs: AchievementDAOs): AchievementDataSource {

    override fun getAllAchievements(userId: Long) = achievementDAOs.getAllAchievements(userId).map { flow -> flow.map { it.toAchievement() } }

    override suspend fun upsertAchievement(achievement: Achievement) = achievementDAOs.upsertAchievement(achievement.toEntity())

    override suspend fun deleteAchievement(achievement: Achievement) = achievementDAOs.deleteAchievement(achievement.toEntity())
}