package com.gpluslf.remindme.core.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.gpluslf.remindme.core.data.database.entities.TagsOnTaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TagsOnTaskDAOs {


    @Query("SELECT * FROM tasks_tags WHERE task_title = :taskTitle AND task_list_title = :listTitle AND task_user_id = :userId")
    fun getAllTagsOnTask(taskTitle: String, listTitle: String, userId: Long): Flow<List<TagsOnTaskEntity>>

    @Upsert
    suspend fun upsertTagOnTask(tagsOnTask: TagsOnTaskEntity)

    @Delete
    suspend fun deleteTagOnTask(tagsOnTask: TagsOnTaskEntity)
}