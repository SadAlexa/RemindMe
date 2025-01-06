package com.gpluslf.remindme.core.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.gpluslf.remindme.core.data.database.entities.TaskEntity
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
}
