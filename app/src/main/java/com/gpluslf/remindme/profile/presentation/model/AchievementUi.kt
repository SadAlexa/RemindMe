package com.gpluslf.remindme.profile.presentation.model

import android.net.Uri
import com.gpluslf.remindme.core.domain.Achievement
import com.gpluslf.remindme.core.domain.User

data class AchievementUi(
    val title: String,

    val userId: Long,

    val body: String,

    val isCompleted: Boolean,

    val percentage: Int
)

fun Achievement.toAchievementUi() = AchievementUi(
    title,
    userId,
    body,
    isCompleted,
    percentage
)