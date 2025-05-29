package com.gpluslf.remindme.core.presentation.model

import com.gpluslf.remindme.core.domain.Tag

data class TagUi(
    val id: String,
    val title: String,
    val listId: String,
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