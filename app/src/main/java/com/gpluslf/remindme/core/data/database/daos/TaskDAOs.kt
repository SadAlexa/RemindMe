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

    @Query("SELECT * FROM tasks WHERE title = :taskTitle AND list_title = :listTitle AND user_id = :userId")
    fun getTaskByTitle(taskTitle: String, listTitle: String, userId: Long): Flow<TaskEntity?>

    @Query("SELECT * FROM tasks WHERE list_title = :listTitle AND user_id = :userId")
    fun getTasksByList(listTitle: String, userId: Long): Flow<List<TaskEntity>>

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


    @Query("SELECT tags.* FROM tags LEFT JOIN tasks_tags ON tags.id = tasks_tags.tag_id WHERE tasks_tags.task_title = :taskTitle AND tasks_tags.task_list_title = :listTitle AND tasks_tags.task_user_id = :userId")
    suspend fun getAllTagsOnTask(
        taskTitle: String,
        listTitle: String,
        userId: Long
    ): List<TagEntity>

    @Upsert
    suspend fun upsertTagOnTask(tagsOnTask: List<TagsOnTaskEntity>)

    @Query("DELETE FROM tasks_tags WHERE task_title = :taskTitle AND task_list_title = :listTitle AND task_user_id = :userId")
    suspend fun deleteTagOnTask(userId: Long, taskTitle: String, listTitle: String)
    
    @Transaction
    suspend fun updateTask(task: Task) {
        val taskEntity = task.toTaskEntity()
        deleteTagOnTask(task.userId, task.title, task.listTitle)
        upsertTask(taskEntity)
        upsertTagOnTask(task.tags.map {
            it.toTagsOnTaskEntity(
                task.title,
                task.listTitle,
                task.userId
            )
        })

    }
}
