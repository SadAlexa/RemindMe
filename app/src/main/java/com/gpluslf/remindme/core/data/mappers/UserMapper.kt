package com.gpluslf.remindme.core.data.mappers

import com.gpluslf.remindme.core.data.database.entities.UserEntity
import com.gpluslf.remindme.core.domain.Image
import com.gpluslf.remindme.core.domain.User

fun UserEntity.toUser() = User(
    id,
    username,
    name,
    email,
    password,
    salt,
    if (image != null) Image(image) else null,
)

fun User.toUserEntity() = UserEntity(
    id,
    username,
    name,
    email,
    password,
    salt,
    image?.bytes
)