package com.gpluslf.remindme.profile.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gpluslf.remindme.core.domain.LoggedUserDataSource
import com.gpluslf.remindme.core.domain.UserDataSource
import com.gpluslf.remindme.profile.presentation.model.ProfileAction
import com.gpluslf.remindme.profile.presentation.model.UserUi
import com.gpluslf.remindme.profile.presentation.model.toUserUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UserState(
    val user: UserUi? = null,
    val isImagePickerVisible: Boolean = false,
    val image: Uri? = null,
    val username: String = "",
)

class UserViewModel(
    private val userId: Long,
    private val userRepository: UserDataSource,
    private val loggedUserRepository: LoggedUserDataSource
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
            userRepository.getUserById(userId).collect {
                _state.update { state ->
                    state.copy(user = it.toUserUi())
                }
            }
        }
    }

    private fun updateImage() {
        viewModelScope.launch {
            userRepository.upsertImage(
                userId,
                state.value.image.toString()
            )
        }
    }

    fun onProfileAction(action: ProfileAction) {
        when (action) {
            is ProfileAction.LogOut -> {
                viewModelScope.launch {
                    loggedUserRepository.deleteLoggedUser()
                }
            }

            is ProfileAction.ShowImagePicker -> {
                _state.update { state -> state.copy(isImagePickerVisible = action.isOpen) }
            }

            is ProfileAction.UpdateImage -> {
                _state.update { state ->
                    state.copy(image = action.image)
                }
                updateImage()
            }

            is ProfileAction.LocalBackupData -> TODO()
            is ProfileAction.SyncData -> TODO()
        }
    }
}