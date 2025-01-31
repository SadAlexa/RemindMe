package com.gpluslf.remindme.core.domain

import kotlinx.coroutines.flow.Flow


interface UserAchievementDataSource {

    fun getAllUserAchievements(userId: Long): Flow<List<UserAchievement>>

    fun getAllUnnotifiedAchievements(
        userId: Long,
    ): Flow<List<UserAchievement>>

    suspend fun updateUserAchievement(userAchievement: List<UserAchievement>)
}