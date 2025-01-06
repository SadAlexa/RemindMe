package com.gpluslf.remindme.home.presentation

import com.gpluslf.remindme.core.domain.TagDataSource
import com.gpluslf.remindme.core.presentation.model.TagUi
import com.gpluslf.remindme.core.presentation.model.toTagUi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TagState(val tags: List<TagUi>)

class TagViewModel(
    private val repository: TagDataSource
) : ViewModel() {
    private val _state = MutableStateFlow(TagState(emptyList()))
    val state = _state.onStart {
        loadData()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = TagState(emptyList())
    )

    private fun loadData() {
        repository.getAllTags("title", 1 /*TODO*/).map {flow ->
            _state.update { state ->
                state.copy(tags = flow.map { it.toTagUi() })
            }
        }
    }

    fun upsertTag(tag: TagUi) = viewModelScope.launch {
        // TODO
    }

    fun deleteTag(tag: TagUi) = viewModelScope.launch {
        // TODO
    }
}