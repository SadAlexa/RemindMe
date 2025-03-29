package com.gpluslf.remindme.home.presentation.model

import android.net.Uri
import androidx.compose.runtime.Immutable

@Immutable
data class TodoListState(
    val id: Long = 0L,

    val title: String = "",

    val body: String? = "",

    val image: Uri? = null,

    val isShared: Boolean = false,

    val sharedUserId: Long? = null,

    val isFavorite: Boolean = false,

    val selectedCategory: CategoryUi? = null,

    val categories: List<CategoryUi> = emptyList(),

    val isPickerVisible: Boolean = false
)