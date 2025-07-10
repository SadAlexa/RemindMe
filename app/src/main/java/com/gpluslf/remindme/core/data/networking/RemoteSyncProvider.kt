package com.gpluslf.remindme.core.data.networking

import com.gpluslf.remindme.core.data.database.daos.SyncDAOs
import com.gpluslf.remindme.core.data.dto.SyncDTO
import com.gpluslf.remindme.core.data.mappers.toCategoryDTO
import com.gpluslf.remindme.core.data.mappers.toCategoryEntity
import com.gpluslf.remindme.core.data.mappers.toListEntity
import com.gpluslf.remindme.core.data.mappers.toNotificationDTO
import com.gpluslf.remindme.core.data.mappers.toNotificationEntity
import com.gpluslf.remindme.core.data.mappers.toTagDTO
import com.gpluslf.remindme.core.data.mappers.toTagEntity
import com.gpluslf.remindme.core.data.mappers.toTagsOnTaskDTO
import com.gpluslf.remindme.core.data.mappers.toTagsOnTaskEntity
import com.gpluslf.remindme.core.data.mappers.toTaskDTO
import com.gpluslf.remindme.core.data.mappers.toTaskEntity
import com.gpluslf.remindme.core.data.mappers.toTodoListDTO
import com.gpluslf.remindme.core.data.mappers.toUserAchievementDTO
import com.gpluslf.remindme.core.data.mappers.toUserAchievementEntity
import com.gpluslf.remindme.core.data.mappers.toUserEntity
import com.gpluslf.remindme.core.domain.ApiService
import com.gpluslf.remindme.core.domain.SyncProvider
import com.gpluslf.remindme.core.domain.networkutils.onError
import com.gpluslf.remindme.core.domain.networkutils.onSuccess

class RemoteSyncProvider(
    private val apiService: ApiService,
    private val syncDao: SyncDAOs
) : SyncProvider {

    override suspend fun downloadData(
        email: String,
        password: String,
        username: String?,
        downloadingCallback: () -> Unit,
        errorCallback: () -> Unit
    ) {
        (if (username != null) apiService.register(email, email, password) else apiService.login(
            email,
            password
        )).onError { errorCallback() }.onSuccess { jwt ->
            println(jwt.accessToken)
            apiService.getUser(jwt.accessToken).onError {
                errorCallback()
            }.onSuccess { user ->
                downloadingCallback()
                apiService.syncDownload(
                    jwt.accessToken,
                ).onError {
                    errorCallback()
                }.onSuccess { syncDto ->
                    syncDao.sync(
                        user.toUserEntity(),
                        syncDto.categories.map { it.toCategoryEntity() },
                        syncDto.lists.map { it.toListEntity() },
                        syncDto.tags.map { it.toTagEntity() },
                        syncDto.tasks.map { it.toTaskEntity() },
                        syncDto.tagsOnTask.map { it.toTagsOnTaskEntity() },
                        syncDto.userAchievements.map { it.toUserAchievementEntity() },
                        syncDto.notifications.map { it.toNotificationEntity() }
                    )
                }
            }
        }
    }

    override suspend fun uploadData(
        email: String,
        password: String,
        userId: Long,
        uploadingCallback: () -> Unit,
        errorCallback: () -> Unit
    ) {
        val categories = syncDao.getAllCategories(userId).map { it.toCategoryDTO() }
        val lists = syncDao.getAllLists(userId).map { it.toTodoListDTO() }
        val tags = syncDao.getAllTags(userId).map { it.toTagDTO() }
        val tasks = syncDao.getAllTasks(userId).map { it.toTaskDTO() }
        val tagsOnTask = syncDao.getAllTagsOnTask(userId).map { it.toTagsOnTaskDTO() }
        val userAchievements =
            syncDao.getAllUserAchievements(userId).map { it.toUserAchievementDTO() }
        val notifications = syncDao.getAllNotifications(userId).map { it.toNotificationDTO() }

        apiService.login(email, password).onError {
            errorCallback()
            println("Login error")
        }.onSuccess { token ->
            val jwt = token.accessToken
            uploadingCallback()
            apiService.syncUpload(
                jwt,
                SyncDTO(
                    categories,
                    lists,
                    tags,
                    tasks,
                    tagsOnTask,
                    userAchievements,
                    notifications
                )
            )
        }
    }
}