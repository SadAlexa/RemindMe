package com.gpluslf.remindme.home.presentation.model

import com.gpluslf.remindme.core.presentation.model.TodoListUi

sealed interface HomeScreenAction {
    data object SaveCategory : HomeScreenAction
    data class ShowDialog(val showDialog: Boolean, val category: CategoryUi? = null) :
        HomeScreenAction

    data class UpdateCategoryTitle(val title: String) : HomeScreenAction
    data class SetCategory(val category: CategoryUi?) : HomeScreenAction
    data class DeleteList(val list: TodoListUi) : HomeScreenAction
    data class EditList(val list: TodoListUi) : HomeScreenAction
    data class DeleteCategory(val category: CategoryUi) : HomeScreenAction
    data class EditCategory(val category: CategoryUi) : HomeScreenAction
}
