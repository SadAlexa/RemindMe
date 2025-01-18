package com.gpluslf.remindme.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gpluslf.remindme.core.domain.UserAchievementDataSource
import com.gpluslf.remindme.profile.presentation.model.UserAchievementUi
import com.gpluslf.remindme.profile.presentation.model.toUserAchievementUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UserAchievementState(val achievements: List<UserAchievementUi>)

class UserAchievementViewModel(
    private val userId: Long,
    private val repository: UserAchievementDataSource
) : ViewModel() {
    private val _state = MutableStateFlow(UserAchievementState(emptyList()))
    val state = _state.onStart {
        loadData()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UserAchievementState(emptyList())
    )

    private fun loadData() {
        viewModelScope.launch {
            repository.getAllUserAchievements(userId).collect { achievement ->
                _state.update { state ->
                    state.copy(achievements = achievement.map { it.toUserAchievementUi() })
                }
            }
        }
    }
}