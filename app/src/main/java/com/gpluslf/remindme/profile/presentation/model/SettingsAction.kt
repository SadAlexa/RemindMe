package com.gpluslf.remindme.profile.presentation.model

import com.gpluslf.remindme.profile.domain.Theme

sealed interface SettingsAction {
    data class ChangeTheme(val theme: Theme) : SettingsAction
}