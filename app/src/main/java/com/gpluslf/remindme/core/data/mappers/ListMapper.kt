package com.gpluslf.remindme.core.data.mappers

import com.gpluslf.remindme.core.data.database.entities.ListEntity
import com.gpluslf.remindme.core.domain.Image
import com.gpluslf.remindme.core.domain.TodoList

fun ListEntity.toTodoList() = TodoList(
    title,
    userId,
    body,
    if (image != null) Image(image) else null,
    isShared,
    sharedUserId,
    isFavorite,
    categoryId
)

fun TodoList.toListEntity() = ListEntity(
    title,
    userId,
    body,
    image?.bytes,
    isShared,
    sharedUserId,
    isFavorite,
    categoryId
)