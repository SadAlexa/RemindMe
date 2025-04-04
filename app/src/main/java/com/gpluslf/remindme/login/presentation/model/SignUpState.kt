package com.gpluslf.remindme.login.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
data class SignUpState(
    val name: String = "",
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
)