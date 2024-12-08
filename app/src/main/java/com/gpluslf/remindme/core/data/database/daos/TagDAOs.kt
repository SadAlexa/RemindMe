package com.gpluslf.remindme.core.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.gpluslf.remindme.core.data.database.entities.TagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDAOs {

    @Query("SELECT * FROM tags WHERE id = :categoryId")
    fun getTagById(categoryId: Long): Flow<TagEntity?>

    @Query("SELECT * FROM tags WHERE list_title = :listTitle AND user_id = :userId")
    fun getAllTags(listTitle: String, userId: Long): Flow<List<TagEntity>>

    @Upsert
    suspend fun upsertTag(tag: TagEntity)

    @Delete
    suspend fun deleteTag(tag: TagEntity)

}