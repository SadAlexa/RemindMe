package com.gpluslf.remindme.core.data.database.daos

import androidx.room.Dao
import androidx.room.Query
import com.gpluslf.remindme.core.data.database.views.UserAchievementView
import kotlinx.coroutines.flow.Flow

@Dao
interface AchievementDAOs {

    @Query("SELECT * FROM user_achievements_view WHERE user_id = :userId")
    fun getAllUserAchievements(userId: Long): Flow<List<UserAchievementView>>

    @Query(
        """
        SELECT user_achievements_view.*
        FROM user_achievements_view LEFT JOIN notifications ON 
        user_achievements_view.achievement_id = notifications.achievement_id AND
        user_achievements_view.user_id = notifications.user_id
        WHERE user_achievements_view.user_id = :userId AND 
        user_achievements_view.isCompleted = 1 AND 
        notifications.achievement_id IS NULL
        """
    )
    fun getAllUnnotifiedAchievements(userId: Long): Flow<List<UserAchievementView>>
}