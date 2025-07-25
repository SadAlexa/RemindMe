package com.gpluslf.remindme.profile.presentation.model

sealed interface SyncEvent {
    data object SyncSuccess : SyncEvent
    data object SyncFailed : SyncEvent
}
