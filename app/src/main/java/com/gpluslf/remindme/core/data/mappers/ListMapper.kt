package com.gpluslf.remindme.core.data.mappers

import android.net.Uri
import com.gpluslf.remindme.core.data.database.entities.CategoryEntity
import com.gpluslf.remindme.core.data.database.entities.ListEntity
import com.gpluslf.remindme.core.domain.TodoList

fun ListEntity.toTodoList(category: CategoryEntity?) = TodoList(
    id,
    title,
    userId,
    body,
    image?.let { Uri.parse(it) },
    isShared,
    sharedUserId,
    isFavorite,
    category?.toCategory()
)

fun TodoList.toListEntity() = ListEntity(
    id,
    title,
    userId,
    body,
    image?.toString(),
    isShared,
    sharedUserId,
    isFavorite,
    category?.id
)