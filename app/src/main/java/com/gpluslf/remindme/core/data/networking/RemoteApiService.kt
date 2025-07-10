package com.gpluslf.remindme.core.data.networking

import com.gpluslf.remindme.core.data.dto.SyncDTO
import com.gpluslf.remindme.core.data.dto.TokenDTO
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

    override suspend fun register(
        username: String,
        email: String,
        password: String,
    ): Result<TokenDTO, NetworkError> {
        return safeCall<TokenDTO> {
            httpClient.post("/auth/register") {
                setBody(
                    mapOf(
                        "username" to username,
                        "email" to email,
                        "password" to password,
                    )
                )
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

    override suspend fun syncDownload(jwt: String): Result<SyncDTO, NetworkError> {
        return safeCall<SyncDTO> {
            httpClient.get("/sync") {
                bearerAuth(jwt)
            }
        }
    }

    override suspend fun syncUpload(jwt: String, syncDto: SyncDTO): Result<Unit, NetworkError> {
        return safeCall<Unit> {
            httpClient.post("/sync") {
                bearerAuth(jwt)
                setBody(syncDto)
            }
        }
    }
}