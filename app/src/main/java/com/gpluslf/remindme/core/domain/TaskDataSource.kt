package com.gpluslf.remindme.core.domain

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate


interface TaskDataSource {
    fun getTaskById(taskId: Long): Flow<Task?>

    fun getTaskByTitle(taskTitle: String, listId: Long, userId: Long): Flow<Task?>

    fun getAllTasksByList(listId: Long, userId: Long): Flow<List<Task>>

    suspend fun getAllTaskByYearMonth(
        start: LocalDate,
        end: LocalDate,
        userId: Long
    ): List<Task>

    suspend fun upsertTask(task: Task)

    suspend fun deleteTask(task: Task)
}