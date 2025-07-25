package com.gpluslf.remindme.core.data.repository

import com.gpluslf.remindme.core.GUEST_USER_ID
import com.gpluslf.remindme.core.data.database.daos.LoggedUserDAOs
import com.gpluslf.remindme.core.data.database.daos.SyncDAOs
import com.gpluslf.remindme.core.data.mappers.toLoggedUserEntity
import com.gpluslf.remindme.core.domain.LoggedUserDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LoggedUserRepository(
    private val loggedUserDAOs: LoggedUserDAOs,
    private val syncDAOs: SyncDAOs,
) : LoggedUserDataSource {
    override fun getLoggedUserById(): Flow<Long?> {
        return loggedUserDAOs.getLoggedUserById().map {
            it?.id
        }
    }

    override suspend fun upsertLoggedUser(userId: Long) {
        loggedUserDAOs.upsertLoggedUser(userId.toLoggedUserEntity())
    }

    override suspend fun deleteLoggedUser(userId: Long) {
        loggedUserDAOs.deleteLoggedUser()
        if (userId != GUEST_USER_ID) {
            syncDAOs.deleteSync(userId = userId)
        }
    }
}