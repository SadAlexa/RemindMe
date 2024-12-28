package com.gpluslf.remindme.home.presentation.model

import com.gpluslf.remindme.core.domain.Category


data class CategoryUi(
    val id: Long,
    val title: String,
    val userId: Long,
    val isSelected: Boolean = false,
)

fun Category.toCategoryUi() = CategoryUi(
    id,
    title,
    userId
)
