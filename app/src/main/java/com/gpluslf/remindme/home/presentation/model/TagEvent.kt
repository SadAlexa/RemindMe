package com.gpluslf.remindme.home.presentation.model

sealed interface TagEvent {
    data object TagCreated : TagEvent
}