package com.gpluslf.remindme.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gpluslf.remindme.core.domain.DataStoreSource
import com.gpluslf.remindme.profile.presentation.model.SettingsAction
import com.gpluslf.remindme.profile.presentation.model.SettingsState
import com.gpluslf.remindme.profile.presentation.model.toThemeUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class SettingsViewModel(
    private val dataStoreSource: DataStoreSource,
) : ViewModel() {
    private val _state = MutableStateFlow(SettingsState())
    val state = _state.onStart {
        loadData()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = SettingsState()
    )

    private fun loadData() {
        viewModelScope.launch {
            dataStoreSource.getInt("theme").collect {
                if (it != null) {
                    _state.update { state ->
                        state.copy(
                            theme = it.toThemeUi()
                        )
                    }
                }
            }
        }
    }

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.ChangeTheme -> {
                viewModelScope.launch {
                    dataStoreSource.putInt("theme", action.theme.value)
                }
            }
        }
    }
}