package com.gpluslf.remindme.login.presentation.model

sealed interface SignInAction {
    data class UpdateEmail(val value: String) : SignInAction
    data class UpdatePassword(val value: String) : SignInAction
}