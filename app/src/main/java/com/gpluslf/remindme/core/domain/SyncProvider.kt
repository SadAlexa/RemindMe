package com.gpluslf.remindme.core.domain

import com.gpluslf.remindme.core.data.dto.ValidateServerDTO
import com.gpluslf.remindme.core.domain.networkutils.NetworkError
import com.gpluslf.remindme.core.domain.networkutils.Result

interface SyncProvider {
    suspend fun downloadData(
        email: String,
        password: String,
        username: String?,
        downloadingCallback: () -> Unit,
        errorCallback: () -> Unit
    )

    suspend fun uploadData(
        email: String,
        password: String,
        userId: Long,
        uploadingCallback: () -> Unit,
        errorCallback: () -> Unit
    )

    suspend fun validateServer(url: String): Result<ValidateServerDTO, NetworkError>
}