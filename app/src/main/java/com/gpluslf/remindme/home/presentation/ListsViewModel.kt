package com.gpluslf.remindme.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gpluslf.remindme.core.domain.ListDataSource
import com.gpluslf.remindme.home.presentation.model.TodoListUi
import com.gpluslf.remindme.home.presentation.model.toTodoListUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ListsState(val lists: List<TodoListUi>)

class ListsViewModel(
    private val userId: Long,
    private val repository: ListDataSource
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
            repository.getAllLists(userId).collect { list ->
                _state.update { state ->
                    state.copy(lists = list.map { it.toTodoListUi() })
                }
            }
        }
    }

    fun upsertList(list: TodoListUi) = viewModelScope.launch {
        // TODO
    }

    fun deleteList(list: TodoListUi) = viewModelScope.launch {
        // TODO
    }
}
