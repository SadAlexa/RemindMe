package com.gpluslf.remindme.core.domain

import kotlinx.coroutines.flow.Flow


interface UserAchievementDataSource {

    fun getAllUserAchievements(userId: Long): Flow<List<UserAchievement>>

}