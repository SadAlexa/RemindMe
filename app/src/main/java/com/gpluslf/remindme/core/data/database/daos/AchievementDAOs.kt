package com.gpluslf.remindme.core.data.database.daos

import androidx.room.Dao
import androidx.room.Query
import com.gpluslf.remindme.core.data.database.views.UserAchievementView
import kotlinx.coroutines.flow.Flow

@Dao
interface AchievementDAOs {

    @Query("SELECT * FROM user_achievements_view WHERE user_id = :userId")
    fun getAllUserAchievements(userId: Long): Flow<List<UserAchievementView>>
}