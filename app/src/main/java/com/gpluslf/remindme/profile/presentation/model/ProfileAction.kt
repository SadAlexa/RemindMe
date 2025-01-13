package com.gpluslf.remindme.profile.presentation.model

sealed interface ProfileAction {
    data object LogOut : ProfileAction
}
