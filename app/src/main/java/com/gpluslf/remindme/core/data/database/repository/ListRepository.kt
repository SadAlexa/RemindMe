package com.gpluslf.remindme.core.data.database.repository

import com.gpluslf.remindme.core.data.database.daos.ListDAOs
import com.gpluslf.remindme.core.data.database.entities.ListEntity
import com.gpluslf.remindme.core.data.mappers.toListEntity
import com.gpluslf.remindme.core.data.mappers.toTodoList
import com.gpluslf.remindme.core.domain.ListDataSource
import com.gpluslf.remindme.core.domain.TodoList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ListRepository(private val listDAOs: ListDAOs) : ListDataSource{
    override fun getAllLists(userId: Long) : Flow<List<TodoList>> {
        return listDAOs.getAllLists(userId).map {
            flow -> flow.map { it.toTodoList() }
        }
    }

    override fun getListByTitle(listTitle: String, userId: Long) : Flow<TodoList?> {
        return listDAOs.getListByTitle(listTitle, userId).map { it?.toTodoList() }
    }

    override suspend fun upsertList(list: TodoList) {
        listDAOs.upsertList(list.toListEntity())
    }

    override suspend fun deleteList(list: TodoList) {
        listDAOs.deleteList(list.toListEntity())
    }

}