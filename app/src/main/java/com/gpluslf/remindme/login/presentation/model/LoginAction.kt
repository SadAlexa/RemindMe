package com.gpluslf.remindme.login.presentation.model

sealed interface LoginAction {
    data object SignIn : LoginAction
    data object SignUp : LoginAction
    data object Guest : LoginAction
}


