package com.gpluslf.remindme.profile.presentation.model

import android.net.Uri

sealed interface ProfileAction {
    data object LogOut : ProfileAction
    data class UpdateImage(val image: Uri) : ProfileAction
    data class ShowImagePicker(val isOpen: Boolean) : ProfileAction
    data object SyncData : ProfileAction
}
