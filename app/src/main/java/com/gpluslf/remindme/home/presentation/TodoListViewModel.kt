package com.gpluslf.remindme.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gpluslf.remindme.core.domain.ListDataSource
import com.gpluslf.remindme.core.domain.TodoList
import com.gpluslf.remindme.home.presentation.model.AddListAction
import com.gpluslf.remindme.home.presentation.model.TodoListState
import com.gpluslf.remindme.home.presentation.model.toCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodoListViewModel(
    private val userId: Long,
    private val todoListDataSource: ListDataSource
) : ViewModel() {
    private val _state = MutableStateFlow(TodoListState())
    val state = _state.asStateFlow()

    private fun saveList() {
        viewModelScope.launch {
            todoListDataSource.upsertList(
                TodoList(
                    userId = userId,
                    title = state.value.title,
                    body = state.value.body,
                    image = state.value.image,
                    category = state.value.selectedCategory?.toCategory(),
                    isShared = state.value.isShared,
                    isFavorite = state.value.isFavorite,
                    sharedUserId = state.value.sharedUserId
                )
            )
        }
    }

    fun onAddListAction(action: AddListAction) {
        when (action) {
            is AddListAction.UpdateTitle -> {
                _state.update { state -> state.copy(title = action.title) }
            }

            is AddListAction.UpdateBody -> {
                _state.update { state -> state.copy(body = action.body) }
            }

            is AddListAction.UpdateImage -> {
                _state.update { state -> state.copy(image = action.image) }
            }

            is AddListAction.UpdateCategory -> {
                _state.update { state -> state.copy(selectedCategory = action.category) }
            }

            is AddListAction.UpdateShared -> {
                _state.update { state -> state.copy(isShared = action.isShared) }
            }

            is AddListAction.UpdateFavorite -> {
                _state.update { state -> state.copy(isFavorite = action.isFavorite) }
            }

            is AddListAction.UpdateSharedUserId -> {
                _state.update { state -> state.copy(sharedUserId = action.sharedUserId) }
            }

            AddListAction.SaveList -> saveList()
        }
    }
}