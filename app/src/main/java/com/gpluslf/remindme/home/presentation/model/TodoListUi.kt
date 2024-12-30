package com.gpluslf.remindme.home.presentation.model

import android.net.Uri
import com.gpluslf.remindme.core.domain.TodoList

data class TodoListUi(
    val title: String,

    val userId: Long,

    val body: String? = null,

    val image: Uri? = null,

    val isShared: Boolean = false,

    val sharedUserId: Long? = null,

    val isFavorite: Boolean = false,

    val category: CategoryUi? = null,

    val taskList: List<TaskUi> = emptyList()
)

fun TodoList.toTodoListUi() = TodoListUi(
    title,
    userId,
    body,
    image,
    isShared,
    sharedUserId,
    isFavorite,
    category?.toCategoryUi(),
)

