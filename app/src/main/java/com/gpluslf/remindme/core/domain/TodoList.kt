package com.gpluslf.remindme.core.domain

import android.net.Uri

data class TodoList(
    val id: Long,
    
    val title: String,

    val userId: Long,

    val body: String? = null,

    val image: Uri? = null,

    val isShared: Boolean = false,

    val sharedUserId: Long? = null,

    val isFavorite: Boolean = false,

    val category: Category? = null
)