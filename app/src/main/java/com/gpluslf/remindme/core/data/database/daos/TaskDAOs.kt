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
import com.gpluslf.remindme.core.data.mappers.toUUID
import com.gpluslf.remindme.core.domain.Task
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface TaskDAOs {
    @Query("SELECT * FROM tasks WHERE id = :taskId ")
    fun getTaskById(taskId: UUID): Flow<TaskEntity?>

    @Query("SELECT * FROM tasks WHERE title = :taskTitle AND list_id = :listId AND user_id = :userId")
    fun getTaskByTitle(taskTitle: String, listId: UUID, userId: Long): Flow<TaskEntity?>

    @Query("SELECT * FROM tasks WHERE list_id = :listId AND user_id = :userId")
    fun getTasksByList(listId: UUID, userId: Long): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE end_time >= :start AND end_time <= :end AND user_id = :userId")
    suspend fun getAllTaskByYearMonth(
        start: Long,
        end: Long,
        userId: Long
    ): List<TaskEntity>

    @Upsert
    suspend fun upsertTask(task: TaskEntity): Long

    @Delete
    suspend fun deleteTask(task: TaskEntity)


    @Query("SELECT tags.* FROM tags LEFT JOIN tasks_tags ON tags.id = tasks_tags.tag_id WHERE tasks_tags.task_id = :taskId AND tasks_tags.task_list_id = :listId AND tasks_tags.task_user_id = :userId")
    suspend fun getAllTagsOnTask(
        taskId: UUID,
        listId: UUID,
        userId: Long
    ): List<TagEntity>

    @Upsert
    suspend fun upsertTagOnTask(tagsOnTask: List<TagsOnTaskEntity>)

    @Query("DELETE FROM tasks_tags WHERE task_id = :taskId AND task_user_id = :userId")
    suspend fun deleteTagOnTask(userId: Long, taskId: UUID)

    @Transaction
    suspend fun updateTask(task: Task) {

        val taskEntity = task.toTaskEntity()
            .copy(id = if (task.id.isEmpty()) UUID.randomUUID() else task.id.toUUID())
        deleteTagOnTask(taskEntity.userId, taskEntity.id)
        upsertTask(taskEntity)
        upsertTagOnTask(task.tags.map {
            it.toTagsOnTaskEntity(
                taskEntity.id.toString(),
                task.listId,
                task.userId
            )
        })
    }
}
