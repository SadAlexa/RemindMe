package com.gpluslf.remindme.login.presentation.model

sealed interface SignUpAction {
    data class UpdateUsername(val value: String) : SignUpAction
    data class UpdateEmail(val value: String) : SignUpAction
    data class UpdatePassword(val value: String) : SignUpAction
    data class UpdateConfirmPassword(val value: String) : SignUpAction
}
