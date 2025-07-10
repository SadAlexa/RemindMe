package com.gpluslf.remindme.core.domain

import com.gpluslf.remindme.core.data.dto.SyncDTO
import com.gpluslf.remindme.core.data.dto.TokenDTO
import com.gpluslf.remindme.core.data.dto.UserDTO
import com.gpluslf.remindme.core.domain.networkutils.NetworkError
import com.gpluslf.remindme.core.domain.networkutils.Result

interface ApiService {

    suspend fun login(
        email: String,
        password: String,
    ): Result<TokenDTO, NetworkError>

    suspend fun register(
        username: String,
        email: String,
        password: String,
    ): Result<TokenDTO, NetworkError>

    suspend fun getUser(
        jwt: String,
    ): Result<UserDTO, NetworkError>

    suspend fun syncDownload(
        jwt: String
    ): Result<SyncDTO, NetworkError>

    suspend fun syncUpload(
        jwt: String,
        syncDto: SyncDTO
    ): Result<Unit, NetworkError>

}