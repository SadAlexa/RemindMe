package com.gpluslf.remindme.core.data.mappers

import android.net.Uri
import com.gpluslf.remindme.core.data.database.entities.CategoryEntity
import com.gpluslf.remindme.core.data.database.entities.ListEntity
import com.gpluslf.remindme.core.data.dto.TodoListDTO
import com.gpluslf.remindme.core.domain.TodoList

fun ListEntity.toTodoList(category: CategoryEntity?) = TodoList(
    id.toString(),
    title,
    userId,
    body,
    image?.let { Uri.parse(it) },
    isShared,
    sharedUserId,
    category?.toCategory()
)

fun TodoList.toListEntity() = ListEntity(
    id.toUUID(),
    title,
    userId,
    body,
    image?.toString(),
    isShared,
    sharedUserId,
    category?.id
)

fun TodoListDTO.toListEntity() = ListEntity(
    id.toUUID(),
    title,
    userId,
    body,
    image?.toString(),
    isShared,
    sharedUserId,
    categoryId
)

fun ListEntity.toTodoListDTO() = TodoListDTO(
    id.toString(),
    title,
    userId,
    body,
    image,
    isShared,
    sharedUserId,
    categoryId
)