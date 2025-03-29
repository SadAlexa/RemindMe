package com.gpluslf.remindme.core.presentation.model

import com.gpluslf.remindme.core.domain.Tag

data class TagUi(
    val id: Long,
    val title: String,
    val listId: Long,
    val userId: Long,
)

fun Tag.toTagUi() = TagUi(
    id,
    title,
    listId,
    userId
)

fun TagUi.toTag() = Tag(
    id,
    title,
    listId,
    userId
)