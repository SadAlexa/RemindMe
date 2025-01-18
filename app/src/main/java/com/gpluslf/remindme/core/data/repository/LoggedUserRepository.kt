package com.gpluslf.remindme.core.data.repository

import com.gpluslf.remindme.core.data.database.daos.LoggedUserDAOs
import com.gpluslf.remindme.core.data.mappers.toLoggedUserEntity
import com.gpluslf.remindme.core.domain.LoggedUserDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LoggedUserRepository(
    private val loggedUserDAOs: LoggedUserDAOs
) : LoggedUserDataSource {
    override fun getLoggedUserById(): Flow<Long?> {
        return loggedUserDAOs.getLoggedUserById().map {
            it?.id
        }
    }

    override suspend fun upsertLoggedUser(id: Long) {
        loggedUserDAOs.upsertLoggedUser(id.toLoggedUserEntity())
    }

    override suspend fun deleteLoggedUser() {
        loggedUserDAOs.deleteLoggedUser()
    }
}