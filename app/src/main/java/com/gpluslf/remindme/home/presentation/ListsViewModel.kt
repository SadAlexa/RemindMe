package com.gpluslf.remindme.home.presentation

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gpluslf.remindme.core.domain.CategoryDataSource
import com.gpluslf.remindme.core.domain.ListDataSource
import com.gpluslf.remindme.core.presentation.model.TodoListUi
import com.gpluslf.remindme.core.presentation.model.toTodoListUi
import com.gpluslf.remindme.home.presentation.model.HomeScreenAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Immutable
data class ListsState(
    val lists: List<TodoListUi>,
    val showDialog: Boolean = false,
    val categoryTitle: String = ""
)

class ListsViewModel(
    private val userId: Long,
    private val listRepository: ListDataSource,
    private val categoryRepository: CategoryDataSource
) : ViewModel() {
    private val _state = MutableStateFlow(ListsState(emptyList()))
    val state = _state.onStart {
        loadData()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ListsState(emptyList())
    )

    private fun loadData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                listRepository.getAllLists(userId).collect { list ->
                    _state.update { state ->
                        state.copy(lists = list.map { it.toTodoListUi() })
                    }
                }
            }
        }
    }

    fun onHomeScreenAction(action: HomeScreenAction) {
        when (action) {
            HomeScreenAction.SaveCategory -> {
                viewModelScope.launch {
                    categoryRepository.upsertCategory(
                        com.gpluslf.remindme.core.domain.Category(
                            id = 0,
                            title = state.value.categoryTitle,
                            userId
                        )
                    )
                }
                _state.update { state -> state.copy(showDialog = false, categoryTitle = "") }
            }

            is HomeScreenAction.ShowDialog -> {
                _state.update { state ->
                    state.copy(
                        showDialog = action.showDialog,
                    )
                }
            }

            is HomeScreenAction.UpdateCategoryTitle -> {
                _state.update { state -> state.copy(categoryTitle = action.title) }
            }

        }
    }
}


