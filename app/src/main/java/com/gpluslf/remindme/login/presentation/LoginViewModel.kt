package com.gpluslf.remindme.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gpluslf.remindme.core.domain.LoggedUserDataSource
import com.gpluslf.remindme.core.domain.User
import com.gpluslf.remindme.core.domain.UserDataSource
import com.gpluslf.remindme.login.presentation.model.LoginAction
import com.gpluslf.remindme.login.presentation.model.LoginEvent
import com.gpluslf.remindme.login.presentation.model.SignInAction
import com.gpluslf.remindme.login.presentation.model.SignInState
import com.gpluslf.remindme.login.presentation.model.SignUpAction
import com.gpluslf.remindme.login.presentation.model.SignUpState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val userRepository: UserDataSource,
    private val loggedUserRepository: LoggedUserDataSource
) : ViewModel() {
    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState = _signUpState.asStateFlow()

    private val _signInState = MutableStateFlow(SignInState())
    val signInState = _signInState.asStateFlow()

    val events = Channel<LoginEvent>()

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

            is SignUpAction.UpdateName -> {
                _signUpState.update { state -> state.copy(name = action.value) }
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
            userRepository.createAccount(
                User(
                    id = 0,
                    username = signUpState.value.username,
                    name = signUpState.value.name,
                    email = signUpState.value.email,
                    password = signUpState.value.password,
                    salt = "",
                    image = null
                )
            )
        }
    }

    fun onLoginAction(action: LoginAction) {
        when (action) {
            LoginAction.Guest -> TODO()
            LoginAction.SignIn -> {
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        val user = userRepository.logInUser(
                            signInState.value.email,
                            signInState.value.password
                        )
                        if (user != null) {
                            loggedUserRepository.upsertLoggedUser(user.id)
                        } else {
                            events.send(LoginEvent.LoginFailed)
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
        }
    }

}