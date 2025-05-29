package com.gpluslf.remindme.core.data.networking

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
import com.gpluslf.remindme.core.domain.ApiService
import com.gpluslf.remindme.core.domain.networkutils.NetworkError
import com.gpluslf.remindme.core.domain.networkutils.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class RemoteApiService(
    private val httpClient: HttpClient,
) : ApiService {
    override suspend fun login(
        email: String,
        password: String,
    ): Result<TokenDTO, NetworkError> {
        return safeCall<TokenDTO> {
            httpClient.post("/auth/login") {
                setBody(
                    mapOf(
                        "email" to email,
                        "password" to password,
                    )
                )
            }
        }
    }

    override suspend fun sync(jwt: String): Result<SyncDTO, NetworkError> {
        return safeCall<SyncDTO> {
            httpClient.get("/sync") {
                bearerAuth(jwt)
            }
        }
    }

    override suspend fun getUser(jwt: String): Result<UserDTO, NetworkError> {
        return safeCall<UserDTO> {
            httpClient.get("/user") {
                bearerAuth(jwt)
            }
        }
    }

    override suspend fun postUser(jwt: String, user: UserDTO): Result<Unit, NetworkError> {
        return safeCall<Unit> {
            httpClient.post("/user") {
                bearerAuth(jwt)
                setBody(user)
            }
        }
    }

    override suspend fun getCategories(
        jwt: String
    ): Result<List<CategoryDTO>, NetworkError> {
        return safeCall<List<CategoryDTO>> {
            httpClient.get("/category") {
                bearerAuth(jwt)
            }
        }
    }

    override suspend fun postCategory(
        jwt: String,
        category: List<CategoryDTO>
    ): Result<Unit, NetworkError> {
        return safeCall<Unit> {
            httpClient.post("/category") {
                bearerAuth(jwt)
                setBody(category)
            }
        }
    }

    override suspend fun getLists(jwt: String): Result<List<TodoListDTO>, NetworkError> {
        return safeCall<List<TodoListDTO>> {
            httpClient.get("/list") {
                bearerAuth(jwt)
            }
        }
    }

    override suspend fun postList(
        jwt: String,
        list: List<TodoListDTO>
    ): Result<Unit, NetworkError> {
        return safeCall<Unit> {
            httpClient.post("/list") {
                bearerAuth(jwt)
                setBody(list)
            }
        }
    }

    override suspend fun getTags(jwt: String): Result<List<TagDTO>, NetworkError> {
        return safeCall<List<TagDTO>> {
            httpClient.get("/tag") {
                bearerAuth(jwt)
            }
        }
    }

    override suspend fun postTag(jwt: String, tag: List<TagDTO>): Result<Unit, NetworkError> {
        return safeCall<Unit> {
            httpClient.post("/tag") {
                bearerAuth(jwt)
                setBody(tag)
            }
        }
    }

    override suspend fun getTasks(jwt: String): Result<List<TaskDTO>, NetworkError> {
        return safeCall<List<TaskDTO>> {
            httpClient.get("/task") {
                bearerAuth(jwt)
            }
        }
    }

    override suspend fun postTask(jwt: String, task: List<TaskDTO>): Result<Unit, NetworkError> {
        return safeCall<Unit> {
            httpClient.post("/task") {
                bearerAuth(jwt)
                setBody(task)
            }
        }
    }

    override suspend fun getTagsOnTask(jwt: String): Result<List<TagsOnTaskDTO>, NetworkError> {
        return safeCall<List<TagsOnTaskDTO>> {
            httpClient.get("/tagsOnTask") {
                bearerAuth(jwt)
            }
        }
    }

    override suspend fun postTagsOnTask(
        jwt: String,
        tagsOnTask: List<TagsOnTaskDTO>
    ): Result<Unit, NetworkError> {
        return safeCall<Unit> {
            httpClient.post("/tagsOnTask") {
                bearerAuth(jwt)
                setBody(tagsOnTask)
            }
        }
    }

    override suspend fun getUserAchievements(jwt: String): Result<List<UserAchievementDTO>, NetworkError> {
        return safeCall<List<UserAchievementDTO>> {
            httpClient.get("/userAchievement") {
                bearerAuth(jwt)
            }
        }
    }

    override suspend fun postUserAchievement(
        jwt: String,
        userAchievement: List<UserAchievementDTO>
    ): Result<Unit, NetworkError> {
        return safeCall<Unit> {
            httpClient.post("/userAchievement") {
                bearerAuth(jwt)
                setBody(userAchievement)
            }
        }
    }

    override suspend fun getNotifications(jwt: String): Result<List<NotificationDTO>, NetworkError> {
        return safeCall<List<NotificationDTO>> {
            httpClient.get("/notification") {
                bearerAuth(jwt)
            }
        }
    }

    override suspend fun postNotification(
        jwt: String,
        notification: List<NotificationDTO>
    ): Result<Unit, NetworkError> {
        return safeCall<Unit> {
            httpClient.post("/notification") {
                bearerAuth(jwt)
                setBody(notification)
            }
        }
    }
}