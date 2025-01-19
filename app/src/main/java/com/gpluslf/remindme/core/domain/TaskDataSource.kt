package com.gpluslf.remindme.core.domain

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate


interface TaskDataSource {

    fun getTaskByTitle(taskTitle: String, listTitle: String, userId: Long): Flow<Task?>

    fun getAllTasksByList(listTitle: String, userId: Long): Flow<List<Task>>

    suspend fun getAllTaskByYearMonth(
        start: LocalDate,
        end: LocalDate,
        userId: Long
    ): List<Task>

    suspend fun upsertTask(task: Task)

    suspend fun deleteTask(task: Task)
}