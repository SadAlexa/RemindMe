package com.gpluslf.remindme.core.domain

import kotlinx.coroutines.flow.Flow

interface UserDataSource {

    fun getUsers(query: String): Flow<List<User>>

    fun getUserById(userId: Long): Flow<User?>

    suspend fun upsertUser(user: User)

    suspend fun deleteUser(user: User)
}