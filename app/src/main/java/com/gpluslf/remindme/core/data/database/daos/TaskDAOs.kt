package com.gpluslf.remindme.core.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.gpluslf.remindme.core.data.database.entities.TagEntity
import com.gpluslf.remindme.core.data.database.entities.TagsOnTaskEntity
import com.gpluslf.remindme.core.data.database.entities.TaskEntity
import com.gpluslf.remindme.core.data.mappers.toTagsOnTaskEntity
import com.gpluslf.remindme.core.data.mappers.toTaskEntity
import com.gpluslf.remindme.core.domain.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDAOs {
    @Query("SELECT * FROM tasks WHERE id = :taskId ")
    fun getTaskById(taskId: Long): Flow<TaskEntity?>

    @Query("SELECT * FROM tasks WHERE title = :taskTitle AND list_id = :listId AND user_id = :userId")
    fun getTaskByTitle(taskTitle: String, listId: Long, userId: Long): Flow<TaskEntity?>

    @Query("SELECT * FROM tasks WHERE list_id = :listId AND user_id = :userId")
    fun getTasksByList(listId: Long, userId: Long): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE end_time >= :start AND end_time <= :end AND user_id = :userId")
    suspend fun getAllTaskByYearMonth(
        start: Long,
        end: Long,
        userId: Long
    ): List<TaskEntity>

    @Upsert
    suspend fun upsertTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)


    @Query("SELECT tags.* FROM tags LEFT JOIN tasks_tags ON tags.id = tasks_tags.tag_id WHERE tasks_tags.task_id = :taskId AND tasks_tags.task_list_id = :listId AND tasks_tags.task_user_id = :userId")
    suspend fun getAllTagsOnTask(
        taskId: Long,
        listId: Long,
        userId: Long
    ): List<TagEntity>

    @Upsert
    suspend fun upsertTagOnTask(tagsOnTask: List<TagsOnTaskEntity>)

    @Query("DELETE FROM tasks_tags WHERE task_id = :taskId AND task_user_id = :userId")
    suspend fun deleteTagOnTask(userId: Long, taskId: Long)

    @Transaction
    suspend fun updateTask(task: Task) {
        val taskEntity = task.toTaskEntity()
        deleteTagOnTask(task.userId, task.id)
        upsertTask(taskEntity)
        upsertTagOnTask(task.tags.map {
            it.toTagsOnTaskEntity(
                task.id,
                task.listId,
                task.userId
            )
        })
    }
}
