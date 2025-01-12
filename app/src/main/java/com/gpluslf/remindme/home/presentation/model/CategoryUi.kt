package com.gpluslf.remindme.home.presentation.model

import com.gpluslf.remindme.core.domain.Category


data class CategoryUi(
    val id: Long,
    val title: String,
    val userId: Long,
)

fun Category.toCategoryUi() = CategoryUi(
    id,
    title,
    userId
)

fun CategoryUi.toCategory() = Category(
    id,
    title,
    userId
)