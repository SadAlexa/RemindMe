package com.gpluslf.remindme.core.domain

import android.net.Uri

data class TodoList(
    val id: String,

    val title: String,

    val userId: Long,

    val body: String? = null,

    val image: Uri? = null,

    val isShared: Boolean = false,

    val sharedUserId: Long? = null,

    val category: Category? = null
)