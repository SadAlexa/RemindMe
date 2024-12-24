package com.gpluslf.remindme.core.domain

import kotlinx.coroutines.flow.Flow


interface AchievementDataSource {

    fun getAllAchievements(userId: Long): Flow<List<Achievement>>

    suspend fun upsertAchievement(achievement: Achievement)

    suspend fun deleteAchievement(achievement: Achievement)
}