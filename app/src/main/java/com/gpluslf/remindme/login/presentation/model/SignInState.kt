package com.gpluslf.remindme.login.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
data class SignInState(
    val email: String = "",
    val password: String = "",
    val loginError: Boolean = false,
    val loadingLabel: String = "",
    val isLoading: Boolean = false,
    val progress: Float = 0f,
)
