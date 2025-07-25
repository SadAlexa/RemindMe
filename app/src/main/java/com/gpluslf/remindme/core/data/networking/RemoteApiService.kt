package com.gpluslf.remindme.core.data.networking

import com.gpluslf.remindme.core.data.dto.SyncDTO
import com.gpluslf.remindme.core.data.dto.TokenDTO
import com.gpluslf.remindme.core.data.dto.UserDTO
import com.gpluslf.remindme.core.data.dto.ValidateServerDTO
import com.gpluslf.remindme.core.domain.ApiService
import com.gpluslf.remindme.core.domain.DataStoreSource
import com.gpluslf.remindme.core.domain.networkutils.NetworkError
import com.gpluslf.remindme.core.domain.networkutils.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.first

class RemoteApiService(
    private val httpClient: HttpClient,
    private val datastore: DataStoreSource
) : ApiService {
    override suspend fun login(
        email: String,
        password: String,
    ): Result<TokenDTO, NetworkError> {
        return safeCall<TokenDTO> {
            httpClient.post("${datastore.getString("endpoint").first()}/auth/login") {
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
            httpClient.post("${datastore.getString("endpoint").first()}/auth/register") {
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
            httpClient.get("${datastore.getString("endpoint").first()}/user") {
                bearerAuth(jwt)
            }
        }
    }

    override suspend fun syncDownload(jwt: String): Result<SyncDTO, NetworkError> {
        return safeCall<SyncDTO> {
            httpClient.get("${datastore.getString("endpoint").first()}/sync") {
                bearerAuth(jwt)
            }
        }
    }

    override suspend fun syncUpload(jwt: String, syncDto: SyncDTO): Result<Unit, NetworkError> {
        return safeCall<Unit> {
            httpClient.post("${datastore.getString("endpoint").first()}/sync") {
                bearerAuth(jwt)
                setBody(syncDto)
            }
        }
    }

    override suspend fun validateServer(url: String): Result<ValidateServerDTO, NetworkError> {
        return safeCall<ValidateServerDTO> {
            httpClient.get("$url/ping")
        }
    }
}