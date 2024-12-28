package com.gpluslf.remindme.home.presentation.model

import android.graphics.Bitmap

sealed interface AddListAction {
    data class UpdateTitle(val title: String) : AddListAction
    data class UpdateBody(val body: String) : AddListAction
    data class UpdateImage(val image: Bitmap) : AddListAction
    data class UpdateShared(val isShared: Boolean) : AddListAction
    data class UpdateSharedUserId(val sharedUserId: Long) : AddListAction
    data class UpdateFavorite(val isFavorite: Boolean) : AddListAction
    data class UpdateCategory(val category: CategoryUi) : AddListAction
}