package com.gpluslf.remindme.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class SyncDTO(
    val categories: List<CategoryDTO>,
    val lists: List<TodoListDTO>,
    val tags: List<TagDTO>,
    val tasks: List<TaskDTO>,
    val tagsOnTasks: List<TagsOnTaskDTO>,
    val userAchievements: List<UserAchievementDTO>,
    val notifications: List<NotificationDTO>,
)
