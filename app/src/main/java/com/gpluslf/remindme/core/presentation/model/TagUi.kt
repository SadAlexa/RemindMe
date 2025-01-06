package com.gpluslf.remindme.core.presentation.model

import com.gpluslf.remindme.core.domain.Tag

data class TagUi(
    val id: Long,
    val title: String,
    val listTitle: String,
    val userId: Long,
    val isSelected: Boolean = false,
)

fun Tag.toTagUi() = TagUi(
    id,
    title,
    listTitle,
    userId
)
