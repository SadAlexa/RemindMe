package com.gpluslf.remindme.home.presentation.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.gpluslf.remindme.core.domain.Image
import com.gpluslf.remindme.core.domain.TodoList
import com.gpluslf.remindme.core.presentation.toBitMap

data class TodoListUi(
    val title: String,

    val userId: Long,

    val body: String? = null,

    val image: Bitmap? = null,

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
    image?.toBitMap(),
    isShared,
    sharedUserId,
    isFavorite,
    category?.toCategoryUi(),
)

