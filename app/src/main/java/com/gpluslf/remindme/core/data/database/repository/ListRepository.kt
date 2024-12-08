package com.gpluslf.remindme.core.data.database.repository

import com.gpluslf.remindme.core.data.database.daos.ListDAOs
import com.gpluslf.remindme.core.data.database.entities.ListEntity

class ListRepository(private val listDAOs: ListDAOs) {

    fun getAllLists(userId: Long) = listDAOs.getAllLists(userId)

    fun getListByTitle(listTitle: String, userId: Long) = listDAOs.getListByTitle(listTitle, userId)

    suspend fun upsertList(list: ListEntity) = listDAOs.upsertList(list)

    suspend fun deleteList(list: ListEntity) = listDAOs.deleteList(list)
}