package com.gpluslf.remindme.home.presentation.model

sealed interface CategoryEvent {
    data object CategoryCreated : CategoryEvent
}