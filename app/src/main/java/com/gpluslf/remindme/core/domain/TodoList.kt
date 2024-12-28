package com.gpluslf.remindme.core.domain

data class TodoList(
    val title: String,

    val userId: Long,

    val body: String? = null,

    val image: Image? = null,

    val isShared: Boolean = false,

    val sharedUserId: Long? = null,

    val isFavorite: Boolean = false,

    val category: Category? = null
)