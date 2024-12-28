package com.gpluslf.remindme.home.presentation.model

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable

@Immutable
data class TodoListState(
    val title: String = "",

    val body: String? = "",

    val image: Bitmap? = null,

    val isShared: Boolean = false,

    val sharedUserId: Long? = null,

    val isFavorite: Boolean = false,

    val selectedCategory: CategoryUi? = null,

    val categories: List<CategoryUi> = emptyList()

)