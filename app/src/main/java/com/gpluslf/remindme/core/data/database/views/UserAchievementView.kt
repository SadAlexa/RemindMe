package com.gpluslf.remindme.core.data.database.views

import androidx.room.ColumnInfo
import androidx.room.DatabaseView


@DatabaseView(
    """
        SELECT achievements.id as achievement_id, 
                achievements.title as achievement_title,
                achievements.body as achievement_body, 
                achievements.number as achievement_number,
                user_achievements.user_id as user_id,
                user_achievements.number as user_number,
                user_achievements.is_completed as isCompleted,
                user_achievements.is_notified as is_notified
        FROM achievements
        JOIN user_achievements ON achievements.id = user_achievements.achievement_id
    """,
    "user_achievements_view"
)
data class UserAchievementView(

    @ColumnInfo(name = "achievement_id")
    val achievementId: Long,

    @ColumnInfo(name = "achievement_title")
    val achievementTitle: String,

    @ColumnInfo(name = "achievement_body")
    val achievementBody: String,

    @ColumnInfo(name = "achievement_number")
    val achievementNumber: Int,

    @ColumnInfo(name = "user_id")
    val userId: Long,

    @ColumnInfo(name = "user_number")
    val userNumber: Int,

    @ColumnInfo(name = "isCompleted")
    val isCompleted: Boolean,

    @ColumnInfo(name = "is_notified")
    val isNotified: Boolean = false,
)
