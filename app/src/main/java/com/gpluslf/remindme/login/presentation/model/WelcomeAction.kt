package com.gpluslf.remindme.login.presentation.model

sealed interface WelcomeAction {
    data class EditServerUrl(val url: String) : WelcomeAction
    data object ValidateServerUrl : WelcomeAction
}
