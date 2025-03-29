package com.gpluslf.remindme.core.domain

data class Tag(
    val id: Long,
    val title: String,
    val listId: Long,
    val userId: Long
)
