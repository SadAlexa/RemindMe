package com.gpluslf.remindme.core.domain

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
}