package com.gpluslf.remindme.core.data.mappers

import com.gpluslf.remindme.core.data.database.entities.CategoryEntity
import com.gpluslf.remindme.core.data.database.entities.ListEntity
import com.gpluslf.remindme.core.domain.Image
import com.gpluslf.remindme.core.domain.TodoList

fun ListEntity.toTodoList(category: CategoryEntity?) = TodoList(
    title,
    userId,
    body,
    if (image != null) Image(image) else null,
    isShared,
    sharedUserId,
    isFavorite,
    category?.toCategory()
)

fun TodoList.toListEntity() = ListEntity(
    title,
    userId,
    body,
    image?.bytes,
    isShared,
    sharedUserId,
    isFavorite,
    category?.id
)