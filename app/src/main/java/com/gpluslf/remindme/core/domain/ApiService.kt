package com.gpluslf.remindme.core.domain

import com.gpluslf.remindme.core.data.dto.CategoryDTO
import com.gpluslf.remindme.core.data.dto.NotificationDTO
import com.gpluslf.remindme.core.data.dto.SyncDTO
import com.gpluslf.remindme.core.data.dto.TagDTO
import com.gpluslf.remindme.core.data.dto.TagsOnTaskDTO
import com.gpluslf.remindme.core.data.dto.TaskDTO
import com.gpluslf.remindme.core.data.dto.TodoListDTO
import com.gpluslf.remindme.core.data.dto.TokenDTO
import com.gpluslf.remindme.core.data.dto.UserAchievementDTO
import com.gpluslf.remindme.core.data.dto.UserDTO
import com.gpluslf.remindme.core.domain.networkutils.NetworkError
import com.gpluslf.remindme.core.domain.networkutils.Result

interface ApiService {

    suspend fun login(
        email: String,
        password: String,
    ): Result<TokenDTO, NetworkError>

    suspend fun sync(jwt: String): Result<SyncDTO, NetworkError>

    suspend fun getUser(
        jwt: String
    ): Result<UserDTO, NetworkError>

    suspend fun postUser(jwt: String, user: UserDTO): Result<Unit, NetworkError>

    suspend fun getCategories(
        jwt: String
    ): Result<List<CategoryDTO>, NetworkError>

    suspend fun postCategory(jwt: String, category: List<CategoryDTO>): Result<Unit, NetworkError>

    suspend fun getLists(jwt: String): Result<List<TodoListDTO>, NetworkError>
    suspend fun postList(jwt: String, list: List<TodoListDTO>): Result<Unit, NetworkError>

    suspend fun getTags(jwt: String): Result<List<TagDTO>, NetworkError>
    suspend fun postTag(jwt: String, tag: List<TagDTO>): Result<Unit, NetworkError>

    suspend fun getTasks(jwt: String): Result<List<TaskDTO>, NetworkError>
    suspend fun postTask(jwt: String, task: List<TaskDTO>): Result<Unit, NetworkError>

    suspend fun getTagsOnTask(
        jwt: String
    ): Result<List<TagsOnTaskDTO>, NetworkError>

    suspend fun postTagsOnTask(
        jwt: String,
        tagsOnTask: List<TagsOnTaskDTO>
    ): Result<Unit, NetworkError>

    suspend fun getUserAchievements(
        jwt: String
    ): Result<List<UserAchievementDTO>, NetworkError>

    suspend fun postUserAchievement(
        jwt: String,
        userAchievement: List<UserAchievementDTO>
    ): Result<Unit, NetworkError>

    suspend fun getNotifications(
        jwt: String
    ): Result<List<NotificationDTO>, NetworkError>

    suspend fun postNotification(
        jwt: String,
        notification: List<NotificationDTO>
    ): Result<Unit, NetworkError>
}