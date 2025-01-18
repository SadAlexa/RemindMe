package com.gpluslf.remindme.core.domain

data class UserAchievement(

    val achievementId: Long,

    val achievementTitle: String,
    
    val achievementBody: String,

    val achievementNumber: Int,

    val userId: Long,

    val userNumber: Int,

    val isCompleted: Boolean
)
