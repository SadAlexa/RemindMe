package com.gpluslf.remindme.profile.presentation.model

import android.net.Uri
import com.gpluslf.remindme.core.domain.User

data class UserUi (
    val id: Long? = null,

    val username: String,

    val name: String? = null,

    val email: String,

    val password: String,

    val salt: String,

    val image: Uri? = null,
)

fun User.toUserUi() = UserUi(
    id,
    username,
    name,
    email,
    password,
    salt,
    image
)