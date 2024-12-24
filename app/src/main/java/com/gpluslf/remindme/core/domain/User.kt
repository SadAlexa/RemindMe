package com.gpluslf.remindme.core.domain

data class User(
    val id: Long? = null,

    val username: String,

    val name: String? = null,

    val email: String,

    val password: String,

    val salt: String,

    val image: Image? = null,
)