package com.gpluslf.remindme.home.presentation.model

import com.gpluslf.remindme.core.presentation.model.TagUi
import com.gpluslf.remindme.core.presentation.model.TaskUi

sealed interface ListScreenAction {
    data object SaveTag : ListScreenAction
    data class ToggleTask(val task: TaskUi) : ListScreenAction
    data class ShowDialog(val showDialog: Boolean, val tag: TagUi? = null) : ListScreenAction
    data class UpdateTagTitle(val tagTitle: String) : ListScreenAction
    data class DeleteTask(val task: TaskUi) : ListScreenAction
    data class EditTask(val task: TaskUi) : ListScreenAction
    data class SelectTag(val tag: TagUi?) : ListScreenAction
    data class DeleteTag(val tag: TagUi) : ListScreenAction
    data class EditTag(val tag: TagUi) : ListScreenAction
}
