package com.gpluslf.remindme.core.domain

import kotlinx.coroutines.flow.Flow

interface DataStoreSource {
    suspend fun putString(key: String, value: String)
    suspend fun putInt(key: String, value: Int)
    suspend fun getString(key: String): Flow<String?>
    suspend fun getInt(key: String): Flow<Int?>
}