package com.gpluslf.remindme.core.domain

import kotlinx.coroutines.flow.Flow


interface TaskDataSource {

    fun getTaskByTitle(taskTitle: String, listTitle: String, userId: Long): Flow<Task?>

    fun getTasksByList(listTitle: String, userId: Long): Flow<List<Task>>

    suspend fun upsertTask(task: Task)

    suspend fun deleteTask(task: Task)
}
