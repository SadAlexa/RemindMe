package com.gpluslf.remindme.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gpluslf.remindme.core.domain.AchievementDataSource
import com.gpluslf.remindme.core.domain.UserDataSource
import com.gpluslf.remindme.home.presentation.model.toTodoListUi
import com.gpluslf.remindme.profile.presentation.model.AchievementUi
import com.gpluslf.remindme.profile.presentation.model.UserUi
import com.gpluslf.remindme.profile.presentation.model.toAchievementUi
import com.gpluslf.remindme.profile.presentation.model.toUserUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AchievementState (val achievements: List<AchievementUi>)

class AchievementViewModel (
    private val userId: Long,
    private val repository: AchievementDataSource
) : ViewModel() {
    private val _state = MutableStateFlow(AchievementState(emptyList()))
    val state = _state.onStart {
        loadData()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = AchievementState(emptyList())
    )

    private fun loadData() {
        viewModelScope.launch {
            repository.getAllAchievements(userId).collect { achievement ->
                _state.update { state ->
                    state.copy(achievements = achievement.map { it.toAchievementUi() })
                }
            }
        }
    }

    fun upsertAchievement(achievement: AchievementUi) = viewModelScope.launch {
        // TODO
    }

    fun deleteAchievement(achievement: AchievementUi)  = viewModelScope.launch {
        // TODO
    }
}