package com.gpluslf.remindme.home.presentation.model

import com.gpluslf.remindme.core.presentation.model.TagUi
import com.gpluslf.remindme.core.presentation.model.TaskUi

sealed interface ListScreenAction {
    data object SaveTag : ListScreenAction
    data class ToggleTask(val task: TaskUi) : ListScreenAction
    data class ShowDialog(val showDialog: Boolean) : ListScreenAction
    data class UpdateTagTitle(val tagTitle: String) : ListScreenAction
    data class SelectTag(val tag: TagUi?) : ListScreenAction
}
