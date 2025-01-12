package com.gpluslf.remindme.core.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.gpluslf.remindme.core.data.database.entities.TagEntity
import com.gpluslf.remindme.core.data.database.entities.TagsOnTaskEntity

@Dao
interface TagsOnTaskDAOs {

    @Query("SELECT tags.* FROM tags LEFT JOIN tasks_tags ON tags.id = tasks_tags.tag_id WHERE tasks_tags.task_title = :taskTitle AND tasks_tags.task_list_title = :listTitle AND tasks_tags.task_user_id = :userId")
    suspend fun getAllTagsOnTask(
        taskTitle: String,
        listTitle: String,
        userId: Long
    ): List<TagEntity>

    @Upsert
    suspend fun upsertTagOnTask(tagsOnTask: TagsOnTaskEntity)

    @Delete
    suspend fun deleteTagOnTask(tagsOnTask: TagsOnTaskEntity)
}

