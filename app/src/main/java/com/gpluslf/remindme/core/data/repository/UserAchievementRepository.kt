package com.gpluslf.remindme.core.data.repository

import com.gpluslf.remindme.core.data.database.daos.AchievementDAOs
import com.gpluslf.remindme.core.data.mappers.toUserAchievement
import com.gpluslf.remindme.core.domain.UserAchievementDataSource
import kotlinx.coroutines.flow.map

class UserAchievementRepository(private val achievementDAOs: AchievementDAOs) :
    UserAchievementDataSource {
    override fun getAllUserAchievements(userId: Long) =
        achievementDAOs.getAllUserAchievements(userId)
            .map { flow -> flow.map { it.toUserAchievement() } }

    override fun getAllUnnotifiedAchievements(
        userId: Long,
    ) = achievementDAOs.getAllUnnotifiedAchievements(userId)
        .map { flow -> flow.map { it.toUserAchievement() } }
}