package com.gpluslf.remindme.login.presentation.model

sealed interface LoginEvent {
    data object LoginFailed : LoginEvent
    data object SignUpSuccess : LoginEvent
}
