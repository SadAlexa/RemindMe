package com.gpluslf.remindme.core.data.mappers

import com.gpluslf.remindme.core.data.database.entities.TagEntity
import com.gpluslf.remindme.core.domain.Tag

fun TagEntity.toTag() = Tag(
    id,
    title,
    listTitle,
    userId
)

fun Tag.toTagEntity() = TagEntity(
    id,
    title,
    listTitle,
    userId
)
