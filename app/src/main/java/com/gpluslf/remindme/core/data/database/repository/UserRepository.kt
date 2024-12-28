package com.gpluslf.remindme.core.data.database.repository

import com.gpluslf.remindme.core.data.database.daos.UserDAOs
import com.gpluslf.remindme.core.data.database.entities.UserEntity
import com.gpluslf.remindme.core.data.mappers.toUser
import com.gpluslf.remindme.core.data.mappers.toUserEntity
import com.gpluslf.remindme.core.domain.User
import com.gpluslf.remindme.core.domain.UserDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(private val userDAOs: UserDAOs): UserDataSource {

    override fun getUsers(query: String): Flow<List<User>> {
        return userDAOs.getAllUsers().map { flow -> flow.map { it.toUser() } }
    }

    override fun getUserById(userId: Long) = userDAOs.getUserById(userId).map { it?.toUser() }

    override suspend fun upsertUser(user: User) = userDAOs.upsertUser(user.toUserEntity())

    override suspend fun deleteUser(user: User) = userDAOs.deleteUser(user.toUserEntity())
}