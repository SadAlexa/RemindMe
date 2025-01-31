package com.gpluslf.remindme.updates.presentation.model

sealed interface NotificationAction {
    data class Click(val item: NotificationUi) : NotificationAction
    data class Delete(val item: NotificationUi) : NotificationAction
}
