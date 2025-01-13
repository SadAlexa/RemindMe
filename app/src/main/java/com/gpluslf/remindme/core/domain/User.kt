package com.gpluslf.remindme.core.domain

import android.net.Uri

data class User(
    val id: Long,

    val username: String,

    val name: String? = null,

    val email: String,

    val password: String,

    val salt: String,

    val image: Uri? = null,
)