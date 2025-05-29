package com.gpluslf.remindme.core.data.mappers

import com.gpluslf.remindme.core.data.database.entities.CategoryEntity
import com.gpluslf.remindme.core.data.dto.CategoryDTO
import com.gpluslf.remindme.core.domain.Category


fun CategoryEntity.toCategory() = Category(
    id.toString(),
    title,
    userId
)

fun CategoryDTO.toCategoryEntity() = CategoryEntity(
    id.toUUID(),
    title,
    userId
)

fun CategoryEntity.toCategoryDTO() = CategoryDTO(
    id.toString(),
    title,
    userId
)

fun Category.toCategoryEntity() = CategoryEntity(
    id.toUUID(),
    title,
    userId
)
