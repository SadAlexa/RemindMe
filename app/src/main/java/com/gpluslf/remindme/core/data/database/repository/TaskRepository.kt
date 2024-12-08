package com.gpluslf.remindme.core.data.database.repository

import com.gpluslf.remindme.core.data.database.daos.TaskDAOs
import com.gpluslf.remindme.core.data.database.entities.TaskEntity

class TaskRepository(private val taskDAOs: TaskDAOs) {

    fun getAllTasksByList(listTitle: String, userId: Long) = taskDAOs.getTasksByList(listTitle, userId)

    fun getTaskByTitle(taskTitle: String, listTitle: String, userId: Long) = taskDAOs.getTaskByTitle(taskTitle, listTitle, userId)

    suspend fun upsertTask(task: TaskEntity) = taskDAOs.upsertTask(task)

    suspend fun deleteTask(task: TaskEntity) = taskDAOs.deleteTask(task)

}