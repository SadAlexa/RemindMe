package com.gpluslf.remindme.core.domain

import kotlinx.coroutines.flow.Flow

interface UserDataSource {

    fun getUsers(query: String): Flow<List<User>>

    fun getUserById(userId: Long): Flow<User>

    suspend fun logInUser(email: String, password: String): User?

    suspend fun createAccount(user: User)

    suspend fun deleteUser(user: User)

    suspend fun upsertImage(userId: Long, image: String)
}