package com.gpluslf.remindme.core.domain

import kotlinx.coroutines.flow.Flow

interface UserDataSource {

    fun getUserById(userId: Long): Flow<User?>

    fun getAllUsers(): Flow<List<User>>

    suspend fun upsertUser(user: User)

    suspend fun deleteUser(user: User)
}