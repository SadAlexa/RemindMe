package com.gpluslf.remindme.core.domain

import kotlinx.coroutines.flow.Flow

interface LoggedUserDataSource {

    fun getLoggedUserById(): Flow<Long?>

    suspend fun upsertLoggedUser(id: Long)

    suspend fun deleteLoggedUser()

}
