package com.gpluslf.remindme.core.data.mappers

import com.gpluslf.remindme.core.data.database.entities.CategoryEntity
import com.gpluslf.remindme.core.domain.Category


fun CategoryEntity.toCategory() = Category(
    id,
    title,
    userId
)

fun Category.toCategoryEntity() = CategoryEntity(
    id,
    title,
    userId
)
