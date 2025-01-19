package com.gpluslf.remindme.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gpluslf.remindme.core.domain.CategoryDataSource
import com.gpluslf.remindme.core.domain.ListDataSource
import com.gpluslf.remindme.core.domain.TodoList
import com.gpluslf.remindme.home.presentation.model.AddListAction
import com.gpluslf.remindme.home.presentation.model.TodoListState
import com.gpluslf.remindme.home.presentation.model.toCategory
import com.gpluslf.remindme.home.presentation.model.toCategoryUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddListViewModel(
    private val userId: Long,
    private val listTitle: String? = null,
    private val todoListDataSource: ListDataSource,
    private val categoryDataSource: CategoryDataSource
) : ViewModel() {
    private val _state = MutableStateFlow(TodoListState())
    val state = _state.onStart {
        loadData()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        TodoListState()
    )

    private fun loadData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (listTitle != null) {
                    todoListDataSource.getListByTitle(listTitle, userId).collect { list ->
                        if (list != null) {
                            _state.update { state ->
                                state.copy(
                                    title = list.title,
                                    body = list.body,
                                    image = list.image,
                                    selectedCategory = list.category?.toCategoryUi(),
                                    isShared = list.isShared,
                                    isFavorite = list.isFavorite,
                                    sharedUserId = list.sharedUserId
                                )
                            }
                        }
                    }
                }
                categoryDataSource.getAllCategories(userId).collect { list ->
                    _state.update { state ->
                        state.copy(categories = list.map { it.toCategoryUi() })
                    }
                }
            }
        }
    }

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
                _state.update { state ->
                    state.copy(
                        selectedCategory = action.category,

                        )
                }
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