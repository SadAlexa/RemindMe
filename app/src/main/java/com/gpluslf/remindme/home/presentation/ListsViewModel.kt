package com.gpluslf.remindme.home.presentation

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gpluslf.remindme.core.domain.Category
import com.gpluslf.remindme.core.domain.CategoryDataSource
import com.gpluslf.remindme.core.domain.ListDataSource
import com.gpluslf.remindme.core.presentation.model.TodoListUi
import com.gpluslf.remindme.core.presentation.model.toTodoList
import com.gpluslf.remindme.core.presentation.model.toTodoListUi
import com.gpluslf.remindme.home.presentation.model.CategoryEvent
import com.gpluslf.remindme.home.presentation.model.CategoryUi
import com.gpluslf.remindme.home.presentation.model.HomeScreenAction
import com.gpluslf.remindme.home.presentation.model.toCategory
import com.gpluslf.remindme.home.presentation.model.toCategoryUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Immutable
data class ListsState(
    val lists: List<TodoListUi>,
    val showDialog: Boolean = false,
    val categoryTitle: String = "",
    val categories: List<CategoryUi> = emptyList(),
    val selectedCategory: CategoryUi? = null,
    val selectedEditCategory: CategoryUi? = null
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

    private var allLists: List<TodoListUi> = emptyList()
    private val _events = Channel<CategoryEvent>()
    val events = _events.receiveAsFlow()

    private fun loadData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                combine(
                    categoryRepository.getAllCategories(userId),
                    listRepository.getAllLists(userId)
                ) { categories, lists ->
                    Pair(
                        categories.map { it.toCategoryUi() },
                        lists.map { it.toTodoListUi() }
                    )
                }.collect { (categories, lists) ->
                    allLists = lists
                    _state.update { state ->
                        state.copy(
                            categories = categories,
                            lists = filterByCategory(state.selectedCategory)
                        )
                    }
                }
            }
        }
    }

    private fun filterByCategory(category: CategoryUi? = null): List<TodoListUi> {
        return allLists.filter { category == null || it.category?.id == category.id }
    }

    fun onHomeScreenAction(action: HomeScreenAction) {
        when (action) {
            HomeScreenAction.SaveCategory -> {
                viewModelScope.launch {
                    categoryRepository.upsertCategory(
                        Category(
                            id = state.value.selectedEditCategory?.id ?: 0,
                            title = state.value.categoryTitle,
                            userId
                        )
                    )
                    _events.send(CategoryEvent.CategoryCreated)
                }
                _state.update { state ->
                    state.copy(
                        showDialog = false,
                        categoryTitle = "",
                        selectedEditCategory = null
                    )
                }
            }

            is HomeScreenAction.ShowDialog -> {
                _state.update { state ->
                    state.copy(
                        showDialog = action.showDialog,
                        selectedEditCategory = action.category,
                        categoryTitle = action.category?.title ?: ""
                    )
                }
            }

            is HomeScreenAction.UpdateCategoryTitle -> {
                _state.update { state ->
                    state.copy(
                        categoryTitle = action.title,
                    )
                }
            }

            is HomeScreenAction.SetCategory -> {
                _state.update { state ->
                    state.copy(
                        selectedCategory = action.category,
                        lists = filterByCategory(action.category)
                    )
                }
            }

            is HomeScreenAction.DeleteList -> {
                viewModelScope.launch {
                    listRepository.deleteList(action.list.toTodoList())
                }
            }

            is HomeScreenAction.EditList -> {
                viewModelScope.launch {
                    listRepository.upsertList(action.list.toTodoList())
                }
            }

            is HomeScreenAction.DeleteCategory -> {
                viewModelScope.launch {
                    categoryRepository.deleteCategory(action.category.toCategory())
                }
            }

            is HomeScreenAction.EditCategory -> {
                viewModelScope.launch {
                    categoryRepository.upsertCategory(action.category.toCategory())
                }
            }
        }
    }
}


