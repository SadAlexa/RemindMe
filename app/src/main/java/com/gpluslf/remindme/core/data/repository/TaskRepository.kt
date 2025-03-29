package com.gpluslf.remindme.core.data.repository

import com.gpluslf.remindme.core.data.database.daos.TaskDAOs
import com.gpluslf.remindme.core.data.mappers.toLong
import com.gpluslf.remindme.core.data.mappers.toTask
import com.gpluslf.remindme.core.data.mappers.toTaskEntity
import com.gpluslf.remindme.core.domain.Task
import com.gpluslf.remindme.core.domain.TaskDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class TaskRepository(private val taskDAOs: TaskDAOs) :
    TaskDataSource {
    override fun getTaskById(taskId: Long): Flow<Task?> {
        return taskDAOs.getTaskById(taskId).map { taskEntity ->
            taskEntity?.toTask(
                taskDAOs.getAllTagsOnTask(taskEntity.id, taskEntity.listId, taskEntity.userId)
            )
        }
    }

    override fun getTaskByTitle(taskTitle: String, listId: Long, userId: Long): Flow<Task?> {
        return taskDAOs.getTaskByTitle(taskTitle, listId, userId).map { taskEntity ->
            taskEntity?.toTask(
                taskDAOs.getAllTagsOnTask(taskEntity.id, taskEntity.listId, taskEntity.userId)
            )
        }
    }

    override fun getAllTasksByList(listId: Long, userId: Long): Flow<List<Task>> {
        return taskDAOs.getTasksByList(listId, userId).map { flow ->
            flow.map { taskEntity ->
                val tags = taskDAOs.getAllTagsOnTask(taskEntity.id, listId, userId)
                taskEntity.toTask(
                    tags
                )
            }
        }
    }

    override suspend fun getAllTaskByYearMonth(
        start: LocalDate,
        end: LocalDate,
        userId: Long
    ): List<Task> {

        return taskDAOs.getAllTaskByYearMonth(start.toLong(), end.toLong(), userId)
            .map { taskEntity ->
                taskEntity.toTask(
                    taskDAOs.getAllTagsOnTask(taskEntity.id, taskEntity.listId, userId)
                )
            }
    }

    override suspend fun upsertTask(task: Task) {
        taskDAOs.updateTask(task)
    }

    override suspend fun deleteTask(task: Task) = taskDAOs.deleteTask(task.toTaskEntity())

}