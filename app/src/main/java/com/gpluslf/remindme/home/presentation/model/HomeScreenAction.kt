package com.gpluslf.remindme.home.presentation.model

sealed interface HomeScreenAction {
    data object SaveCategory : HomeScreenAction
    data class ShowDialog(val showDialog: Boolean) : HomeScreenAction
    data class UpdateCategoryTitle(val title: String) : HomeScreenAction
    data class SetCategory(val category: CategoryUi?) : HomeScreenAction
}
