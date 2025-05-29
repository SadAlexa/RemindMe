package com.gpluslf.remindme.core.data.repository

import com.gpluslf.remindme.core.data.database.daos.UserDAOs
import com.gpluslf.remindme.core.data.mappers.toLoggedUserEntity
import com.gpluslf.remindme.core.data.mappers.toUser
import com.gpluslf.remindme.core.domain.User
import com.gpluslf.remindme.core.domain.UserDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(private val userDAOs: UserDAOs) : UserDataSource {

    override fun getUsers(query: String): Flow<List<User>> {
        return userDAOs.getAllUsers().map { flow -> flow.map { it.toUser() } }
    }

    override fun getUserById(userId: Long): Flow<User> =
        userDAOs.getUserById(userId).map { it.toUser() }

    override suspend fun logInUser(): User? {
        return userDAOs.logInUser()?.toUser()
    }

    override suspend fun createAccount(user: User) =
        userDAOs.createAccount(user.toLoggedUserEntity())

    override suspend fun deleteUser(user: User) = userDAOs.deleteUser(user.toLoggedUserEntity())

    override suspend fun upsertImage(userId: Long, image: String) {
        userDAOs.upsertImage(userId, image)
    }
}