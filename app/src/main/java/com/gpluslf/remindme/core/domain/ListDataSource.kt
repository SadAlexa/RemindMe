package com.gpluslf.remindme.core.domain

import kotlinx.coroutines.flow.Flow

interface ListDataSource {
    fun getListById(listId: String): Flow<TodoList?>

    fun getAllLists(userId: Long): Flow<List<TodoList>>

    fun getListByTitle(listTitle: String, userId: Long): Flow<TodoList?>

    suspend fun upsertList(list: TodoList)

    suspend fun deleteList(list: TodoList)
}