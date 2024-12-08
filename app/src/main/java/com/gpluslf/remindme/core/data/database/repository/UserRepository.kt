package com.gpluslf.remindme.core.data.database.repository

import com.gpluslf.remindme.core.data.database.daos.UserDAOs
import com.gpluslf.remindme.core.data.database.entities.UserEntity
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDAOs: UserDAOs) {

    val users: Flow<List<UserEntity>> = userDAOs.getAllUsers()

    fun getUserById(id: Long) = userDAOs.getUserById(id)

    suspend fun upsertUser(user: UserEntity) = userDAOs.upsertUser(user)

    suspend fun deleteUser(user: UserEntity) = userDAOs.deleteUser(user)
}