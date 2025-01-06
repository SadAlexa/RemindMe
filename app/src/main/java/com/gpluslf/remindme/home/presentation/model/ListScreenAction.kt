package com.gpluslf.remindme.home.presentation.model

import com.gpluslf.remindme.core.presentation.model.TaskUi

sealed interface ListScreenAction {
    data class ToggleTask(val task: TaskUi): ListScreenAction
}
