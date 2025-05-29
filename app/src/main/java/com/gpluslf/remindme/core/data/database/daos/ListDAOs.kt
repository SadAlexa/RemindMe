package com.gpluslf.remindme.core.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.gpluslf.remindme.core.data.database.entities.ListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ListDAOs {

    @Query("SELECT * FROM lists WHERE id = :listId")
    fun getListById(listId: String): Flow<ListEntity?>

    @Query("SELECT * FROM lists WHERE title = :listTitle AND user_id = :userId")
    fun getListByTitle(listTitle: String, userId: Long): Flow<ListEntity?>

    @Query("SELECT * FROM lists WHERE user_id = :userId")
    fun getAllLists(userId: Long): Flow<List<ListEntity>>

    @Upsert
    suspend fun upsertList(list: ListEntity)

    @Delete
    suspend fun deleteList(list: ListEntity)
}