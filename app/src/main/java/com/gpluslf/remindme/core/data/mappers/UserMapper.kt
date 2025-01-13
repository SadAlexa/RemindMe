package com.gpluslf.remindme.core.data.mappers

import android.net.Uri
import com.gpluslf.remindme.core.data.database.entities.UserEntity
import com.gpluslf.remindme.core.domain.User

fun UserEntity.toUser() = User(
    id,
    username,
    name,
    email,
    password,
    salt,
    image?.let { Uri.parse(it) },
)

fun User.toLoggedUserEntity() = UserEntity(
    id,
    username,
    name,
    email,
    password,
    salt,
    image?.toString(),
)