package com.gpluslf.remindme.profile.presentation

import android.net.Uri
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gpluslf.remindme.core.data.crypt.EncryptedUser
import com.gpluslf.remindme.core.domain.LoggedUserDataSource
import com.gpluslf.remindme.core.domain.SyncProvider
import com.gpluslf.remindme.core.domain.UserDataSource
import com.gpluslf.remindme.profile.presentation.model.ProfileAction
import com.gpluslf.remindme.profile.presentation.model.SyncEvent
import com.gpluslf.remindme.profile.presentation.model.UserUi
import com.gpluslf.remindme.profile.presentation.model.toUserUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UserState(
    val user: UserUi? = null,
    val isImagePickerVisible: Boolean = false,
    val image: Uri? = null,
    val username: String = "",
    val syncing: Boolean = false
)

class UserViewModel(
    private val userId: Long,
    private val userRepository: UserDataSource,
    private val loggedUserRepository: LoggedUserDataSource,
    private val syncProvider: SyncProvider,
    private val encryptedDataStore: DataStore<EncryptedUser>
) : ViewModel() {
    private val _state = MutableStateFlow(UserState())
    val state = _state.onStart {
        loadData()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UserState()
    )

    private val _events = Channel<SyncEvent>()
    val events = _events.receiveAsFlow()

    private fun loadData() {
        viewModelScope.launch {
            userRepository.getUserById(userId).collect {
                _state.update { state ->
                    state.copy(user = it?.toUserUi())
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
                    loggedUserRepository.deleteLoggedUser(userId)
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

            is ProfileAction.SyncData -> {
                encryptedDataStore.data.onEach { values ->
                    _state.update { state ->
                        state.copy(syncing = true)
                    }
                    syncProvider.uploadData(
                        values.email,
                        values.password,
                        userId,
                        {

                        },
                        {
                            viewModelScope.launch {
                                _events.send(SyncEvent.SyncFailed)
                            }
                        },
                    )
                    _state.update { state ->
                        state.copy(syncing = false)
                    }
                    _events.send(SyncEvent.SyncSuccess)
                }.launchIn(viewModelScope)
            }
        }
    }
}