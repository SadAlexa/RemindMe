package com.gpluslf.remindme.core.domain

import kotlinx.coroutines.flow.Flow

interface LoggedUserDataSource {

    fun getLoggedUserById(): Flow<Long?>

    suspend fun upsertLoggedUser(userId: Long)

    suspend fun deleteLoggedUser(userId: Long)

}
