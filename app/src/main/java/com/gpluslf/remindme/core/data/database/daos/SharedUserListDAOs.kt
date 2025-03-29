package com.gpluslf.remindme.core.data.database.daos;

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.gpluslf.remindme.core.data.database.entities.SharedUserListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SharedUserListDAOs {

    @Query("SELECT * FROM shared_user_list WHERE user_id = :userId AND list_shared_user_id = :sharedUserId")
    fun getAllSharedLists(userId: Long, sharedUserId: Long): Flow<List<SharedUserListEntity>>

    @Query("SELECT * FROM shared_user_list WHERE user_id = :userId AND list_shared_user_id = :sharedUserId AND list_id = :listId")
    fun getSharedList(userId: Long, sharedUserId: Long, listId: Long): Flow<SharedUserListEntity>

    @Upsert
    suspend fun upsertSharedList(sharedUserList: SharedUserListEntity)

    @Delete
    suspend fun deleteSharedList(sharedUserList: SharedUserListEntity)
}
