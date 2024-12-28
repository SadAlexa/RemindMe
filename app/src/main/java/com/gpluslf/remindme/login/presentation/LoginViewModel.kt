package com.gpluslf.remindme.login.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.gpluslf.remindme.login.presentation.model.LoginAction
import com.gpluslf.remindme.login.presentation.model.SignUpAction
import com.gpluslf.remindme.login.presentation.model.SignUpState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel: ViewModel() {
    private val _state = MutableStateFlow(SignUpState())
    val state = _state.asStateFlow()

    fun onSignUpAction(action: SignUpAction) {
        when (action) {
            is SignUpAction.UpdateUsername -> {
                _state.value = state.value.copy(username = action.value)
            }
            is SignUpAction.UpdatePassword -> {
                _state.value = state.value.copy(password = action.value)
            }
            is SignUpAction.UpdateConfirmPassword -> {
                _state.value = state.value.copy(confirmPassword = action.value)
            }
            is SignUpAction.UpdateName -> {
                _state.value = state.value.copy(name = action.value)
            }
            is SignUpAction.UpdateEmail -> {
                _state.value = state.value.copy(email = action.value)
            }
        }
    }

    fun onLoginAction(action: LoginAction) {
        when (action) {
            LoginAction.Guest -> TODO()
            LoginAction.SignIn -> TODO()
            LoginAction.SignUp -> TODO()
        }
    }
}