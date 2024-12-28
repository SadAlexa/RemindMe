package com.gpluslf.remindme.home.presentation

import com.gpluslf.remindme.home.presentation.model.AddListAction
import com.gpluslf.remindme.home.presentation.model.TodoListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TodoListViewModel {private val _state = MutableStateFlow(TodoListState())
    val state = _state.asStateFlow()

    fun onAddListAction(action: AddListAction) {
        when (action) {
            is AddListAction.UpdateTitle -> {
                _state.value = state.value.copy(title = action.title)
            }
            is AddListAction.UpdateBody -> {
                _state.value = state.value.copy(body = action.body)
            }
            is AddListAction.UpdateImage -> {
                _state.value = state.value.copy(image = action.image)
            }
            is AddListAction.UpdateCategory -> {
                _state.value = state.value.copy(selectedCategory = action.category)
            }
            is AddListAction.UpdateShared -> {
                _state.value = state.value.copy(isShared = action.isShared)
            }
            is AddListAction.UpdateFavorite -> {
                _state.value = state.value.copy(isFavorite = action.isFavorite)
            }
            is AddListAction.UpdateSharedUserId -> {
                _state.value = state.value.copy(sharedUserId = action.sharedUserId)
            }
        }
    }
}