package com.gpluslf.remindme.core.data.networking

import com.gpluslf.remindme.core.data.database.daos.SyncDAOs
import com.gpluslf.remindme.core.data.database.entities.CategoryEntity
import com.gpluslf.remindme.core.data.database.entities.ListEntity
import com.gpluslf.remindme.core.data.database.entities.NotificationEntity
import com.gpluslf.remindme.core.data.database.entities.TagEntity
import com.gpluslf.remindme.core.data.database.entities.TagsOnTaskEntity
import com.gpluslf.remindme.core.data.database.entities.TaskEntity
import com.gpluslf.remindme.core.data.database.entities.UserAchievementEntity
import com.gpluslf.remindme.core.data.database.entities.UserEntity
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
import com.gpluslf.remindme.core.data.mappers.toUserDTO
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
        downloadUserCallback: () -> Unit,
        downloadCategoryCallback: () -> Unit,
        downloadListCallback: () -> Unit,
        downloadTagCallback: () -> Unit,
        downloadTaskCallback: () -> Unit,
        downloadTagsOnTaskCallback: () -> Unit,
        downloadUserAchievementCallback: () -> Unit,
        downloadNotificationCallback: () -> Unit,
        errorCallback: () -> Unit
    ) {
        apiService.login(email, password).onError { errorCallback() }.onSuccess { jwt ->
            println(jwt.accessToken)
            var user: UserEntity? = null
            val categories = mutableListOf<CategoryEntity>()
            val lists = mutableListOf<ListEntity>()
            val tags = mutableListOf<TagEntity>()
            val tasks = mutableListOf<TaskEntity>()
            val tagsOnTask = mutableListOf<TagsOnTaskEntity>()
            val userAchievements = mutableListOf<UserAchievementEntity>()
            val notifications = mutableListOf<NotificationEntity>()

            // download user
            downloadUserCallback()
            apiService.getUser(jwt.accessToken).onError {
                errorCallback()
            }.onSuccess {
                user = it.toUserEntity()
            }

            // download categories
            downloadCategoryCallback()
            apiService.getCategories(jwt.accessToken)
                .onError { println("error categories") }.onSuccess {
                    categories.addAll(it.map { it.toCategoryEntity() })
                    println(categories)
                }

            // download lists
            downloadListCallback()
            apiService.getLists(jwt.accessToken)
                .onError { println("error lists") }.onSuccess {
                    lists.addAll(it.map { it.toListEntity() })
                }

            // download tags
            downloadTagCallback()
            apiService.getTags(jwt.accessToken)
                .onError { println("error tags") }.onSuccess {
                    tags.addAll(it.map { it.toTagEntity() })
                }

            // download tasks
            downloadTaskCallback()
            apiService.getTasks(jwt.accessToken)
                .onError { println("error tasks") }.onSuccess {
                    tasks.addAll(it.map { it.toTaskEntity() })
                }

            // download tags on task
            downloadTagsOnTaskCallback()
            apiService.getTagsOnTask(jwt.accessToken)
                .onError { println("error tags on task") }.onSuccess {
                    tagsOnTask.addAll(it.map { it.toTagsOnTaskEntity() })
                }

            // download user achievements
            downloadUserAchievementCallback()
            apiService.getUserAchievements(jwt.accessToken)
                .onError { println("error user achievements") }.onSuccess {
                    userAchievements.addAll(it.map { it.toUserAchievementEntity() })
                }

            // download notifications
            downloadNotificationCallback()
            apiService.getNotifications(jwt.accessToken)
                .onError { println("error notifications") }.onSuccess {
                    notifications.addAll(it.map { it.toNotificationEntity() })
                }

            if (user != null) {
                syncDao.sync(
                    user,
                    categories,
                    lists,
                    tags,
                    tasks,
                    tagsOnTask,
                    userAchievements,
                    notifications
                )
            }
        }

    }

    override suspend fun uploadData(
        email: String,
        password: String,
        userId: Long,
        uploadUserCallback: () -> Unit,
        uploadCategoryCallback: () -> Unit,
        uploadListCallback: () -> Unit,
        uploadTagCallback: () -> Unit,
        uploadTaskCallback: () -> Unit,
        uploadTagsOnTaskCallback: () -> Unit,
        uploadUserAchievementCallback: () -> Unit,
        uploadNotificationCallback: () -> Unit,
        errorCallback: () -> Unit
    ) {
        val user = syncDao.getUser(userId).toUserDTO()
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

            // upload user
            apiService.postUser(jwt, user).onError {
                errorCallback()
                println("User error")
            }.onSuccess {
                uploadUserCallback()
            }

            // upload categories
            apiService.postCategory(jwt, categories).onError {
                errorCallback()
                println("Category error")
            }.onSuccess {
                uploadCategoryCallback()
            }

            // upload lists
            apiService.postList(jwt, lists).onError {
                errorCallback()
                println("List error")
            }.onSuccess {
                uploadListCallback()
            }

            // upload tags
            apiService.postTag(jwt, tags).onError {
                errorCallback()
                println("Tag error")
            }.onSuccess {
                uploadTagCallback()
            }

            // upload tasks
            apiService.postTask(jwt, tasks).onError {
                errorCallback()
                println("Task error")
            }.onSuccess {
                uploadTaskCallback()
            }

            // upload tags on task
            apiService.postTagsOnTask(jwt, tagsOnTask).onError {
                errorCallback()
                println("Tags on task error")
            }.onSuccess {
                uploadTagsOnTaskCallback()
            }

            // upload user achievements
            apiService.postUserAchievement(jwt, userAchievements)
                .onError {
                    errorCallback()
                    println("User achievements error")
                }.onSuccess {
                    uploadUserAchievementCallback()
                }

            // upload notifications
            apiService.postNotification(jwt, notifications).onError {
                errorCallback()
                println("Notification error")
            }.onSuccess {
                uploadNotificationCallback()
            }
        }
    }
}