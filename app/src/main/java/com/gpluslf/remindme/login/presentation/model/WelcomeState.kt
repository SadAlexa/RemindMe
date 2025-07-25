package com.gpluslf.remindme.login.presentation.model

data class WelcomeState(
    val serverUrl: String = "",
    val isServerUrlValid: Boolean = false,
    val isError: Boolean = false,
    val loading: Boolean = false
)
