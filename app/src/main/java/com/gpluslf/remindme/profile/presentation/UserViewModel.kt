package com.gpluslf.remindme.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gpluslf.remindme.core.domain.UserDataSource
import com.gpluslf.remindme.profile.presentation.model.UserUi
import com.gpluslf.remindme.profile.presentation.model.toUserUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UserState (val user: UserUi? = null)

class UserViewModel(
    private val userId: Long,
    private val repository: UserDataSource
) : ViewModel() {
    private val _state = MutableStateFlow(UserState())
    val state = _state.onStart {
        loadData()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UserState()
    )

    private fun loadData() {
        viewModelScope.launch {
            repository.getUserById(userId).collect {
                _state.update { state ->
                    state.copy(user = it.toUserUi())
                }
            }
        }
    }

    fun upsertUser(user: UserUi) = viewModelScope.launch {
        // TODO
    }

    fun deleteUser(user: UserUi)  = viewModelScope.launch {
        // TODO
    }
}