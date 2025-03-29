package com.gpluslf.remindme.core.presentation.model

import android.net.Uri
import com.gpluslf.remindme.core.domain.TodoList
import com.gpluslf.remindme.home.presentation.model.CategoryUi
import com.gpluslf.remindme.home.presentation.model.toCategory
import com.gpluslf.remindme.home.presentation.model.toCategoryUi

data class TodoListUi(
    val id: Long,

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
    id,
    title,
    userId,
    body,
    image,
    isShared,
    sharedUserId,
    isFavorite,
    category?.toCategoryUi(),
)

fun TodoListUi.toTodoList() = TodoList(
    id,
    title,
    userId,
    body,
    image,
    isShared,
    sharedUserId,
    isFavorite,
    category?.toCategory(),
)

