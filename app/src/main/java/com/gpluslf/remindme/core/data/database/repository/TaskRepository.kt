package com.gpluslf.remindme.core.data.database.repository

import com.gpluslf.remindme.core.data.database.daos.TagsOnTaskDAOs
import com.gpluslf.remindme.core.data.database.daos.TaskDAOs
import com.gpluslf.remindme.core.data.mappers.toLong
import com.gpluslf.remindme.core.data.mappers.toTask
import com.gpluslf.remindme.core.data.mappers.toTaskEntity
import com.gpluslf.remindme.core.domain.Task
import com.gpluslf.remindme.core.domain.TaskDataSource
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class TaskRepository(private val taskDAOs: TaskDAOs, private val tagsOnTaskDAOs: TagsOnTaskDAOs) :
    TaskDataSource {

    override fun getAllTasksByList(listTitle: String, userId: Long) =
        taskDAOs.getTasksByList(listTitle, userId).map { flow ->
            flow.map { taskEntity ->
                taskEntity.toTask(
                    tagsOnTaskDAOs.getAllTagsOnTask(taskEntity.title, listTitle, userId)
                )
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
                    tagsOnTaskDAOs.getAllTagsOnTask(taskEntity.title, taskEntity.listTitle, userId)
                )
            }
    }

    override suspend fun upsertTask(task: Task) = taskDAOs.upsertTask(task.toTaskEntity())

    override suspend fun deleteTask(task: Task) = taskDAOs.deleteTask(task.toTaskEntity())

}