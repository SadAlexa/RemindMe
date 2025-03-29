package com.gpluslf.remindme.core.data.repository

import com.gpluslf.remindme.core.data.database.daos.SharedUserListDAOs
import com.gpluslf.remindme.core.data.database.entities.SharedUserListEntity

class SharedUserListRepository(private val sharedUserListDAOs: SharedUserListDAOs) {

    fun getAllSharedLists(userId: Long, sharedUserId: Long) =
        sharedUserListDAOs.getAllSharedLists(userId, sharedUserId)

    fun getSharedList(userId: Long, sharedUserId: Long, listId: Long) =
        sharedUserListDAOs.getSharedList(userId, sharedUserId, listId)

    suspend fun upsertSharedList(sharedUserList: SharedUserListEntity) =
        sharedUserListDAOs.upsertSharedList(sharedUserList)

    suspend fun deleteSharedList(sharedUserList: SharedUserListEntity) =
        sharedUserListDAOs.deleteSharedList(sharedUserList)
}