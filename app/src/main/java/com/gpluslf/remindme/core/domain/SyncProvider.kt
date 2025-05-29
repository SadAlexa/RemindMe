package com.gpluslf.remindme.core.domain

interface SyncProvider {
    suspend fun downloadData(
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
    )

    suspend fun uploadData(
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
    )
}