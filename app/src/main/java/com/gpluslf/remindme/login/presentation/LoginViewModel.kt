package com.gpluslf.remindme.login.presentation

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gpluslf.remindme.core.data.crypt.EncryptedUser
import com.gpluslf.remindme.core.domain.LoggedUserDataSource
import com.gpluslf.remindme.core.domain.SyncProvider
import com.gpluslf.remindme.core.domain.UserDataSource
import com.gpluslf.remindme.login.presentation.model.LoginAction
import com.gpluslf.remindme.login.presentation.model.LoginEvent
import com.gpluslf.remindme.login.presentation.model.SignInAction
import com.gpluslf.remindme.login.presentation.model.SignInState
import com.gpluslf.remindme.login.presentation.model.SignUpAction
import com.gpluslf.remindme.login.presentation.model.SignUpState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val userRepository: UserDataSource,
    private val loggedUserRepository: LoggedUserDataSource,
    private val syncProvider: SyncProvider,
    private val encryptedDataStore: DataStore<EncryptedUser>
) : ViewModel() {
    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState = _signUpState.asStateFlow()

    private val _signInState = MutableStateFlow(SignInState())
    val signInState = _signInState.asStateFlow()

    private val _events = Channel<LoginEvent>()
    val events = _events.receiveAsFlow()

    fun onSignUpAction(action: SignUpAction) {
        when (action) {
            is SignUpAction.UpdateUsername -> {
                _signUpState.update { state -> state.copy(username = action.value) }
            }

            is SignUpAction.UpdatePassword -> {
                _signUpState.update { state -> state.copy(password = action.value) }
            }

            is SignUpAction.UpdateConfirmPassword -> {
                _signUpState.update { state -> state.copy(confirmPassword = action.value) }
            }

            is SignUpAction.UpdateEmail -> {
                _signUpState.update { state -> state.copy(email = action.value) }
            }
        }
    }

    fun onSignInAction(action: SignInAction) {
        when (action) {
            is SignInAction.UpdateEmail -> {
                _signInState.update { state -> state.copy(email = action.value) }
            }

            is SignInAction.UpdatePassword ->
                _signInState.update { state -> state.copy(password = action.value) }
        }
    }

    private fun createUser() {
        viewModelScope.launch {
            syncProvider.downloadData(
                signUpState.value.email,
                signUpState.value.password,
                signUpState.value.username,
                {},
                {
                    onError()
                }
            )
            _events.send(LoginEvent.SignUpSuccess)
        }
    }

    private fun updateText(text: String) {
        _signInState.update {
            it.copy(loadingLabel = text)
        }
    }

    fun onError() {
        viewModelScope.launch {
            _events.send(LoginEvent.LoginFailed)
        }
    }

    private fun updateProgress(progress: Float) {
        _signInState.update {
            it.copy(progress = progress)
        }
    }

    fun onLoginAction(action: LoginAction) {
        when (action) {
            LoginAction.SignIn -> {
                viewModelScope.launch {
                    encryptedDataStore.updateData {
                        it.copy(
                            email = signInState.value.email,
                            password = signInState.value.password
                        )
                    }
                    _signInState.update {
                        it.copy(
                            isLoading = true,
                            progress = 0f
                        )
                    }
                    syncProvider.downloadData(
                        signInState.value.email,
                        signInState.value.password,
                        null,
                        {
                            updateText("Downloading data...")
                        },
                        {
                            onError()
                        }
                    )

                    withContext(Dispatchers.IO) {
                        for (i in 1..10) {
                            updateProgress(i.toFloat() / 10)
                            delay(10)
                        }
                        _signInState.update {
                            it.copy(
                                isLoading = false,
                            )
                        }
                        val user = userRepository.logInUser()
                        if (user != null) {
                            loggedUserRepository.upsertLoggedUser(user.id)
                        } else {
                            _events.send(LoginEvent.LoginFailed)
                        }
                        _signInState.update { state ->
                            state.copy(
                                loginError = user == null,
                                isLoggedIn = user != null
                            )
                        }
                    }
                }
            }

            LoginAction.SignUp -> createUser()
            else -> Unit
        }
    }

}