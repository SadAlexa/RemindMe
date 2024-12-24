package com.gpluslf.remindme.core.domain;

import kotlinx.coroutines.flow.Flow


interface SharedUserListDataSource {

    fun getAllSharedLists(userId: Long, sharedUserId: Long): Flow<List<SharedUserList>>

    fun getSharedList(userId: Long, sharedUserId: Long, listTitle: String): Flow<SharedUserList>

    suspend fun upsertSharedList(sharedUserList: SharedUserList)

    suspend fun deleteSharedList(sharedUserList: SharedUserList)
}
