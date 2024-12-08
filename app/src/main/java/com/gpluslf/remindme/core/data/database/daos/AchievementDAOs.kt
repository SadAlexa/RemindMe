package com.gpluslf.remindme.core.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.gpluslf.remindme.core.data.database.entities.AchievementEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AchievementDAOs {

    @Query("SELECT * FROM achievements WHERE user_id = :userId")
    fun getAllAchievements(userId: Long): Flow<List<AchievementEntity>>

    @Upsert
    suspend fun upsertAchievement(achievement: AchievementEntity)

    @Delete
    suspend fun deleteAchievement(achievement: AchievementEntity)
}