package com.gpluslf.remindme.core.data.mappers

import androidx.core.net.toUri
import com.gpluslf.remindme.core.data.database.entities.UserEntity
import com.gpluslf.remindme.core.data.dto.UserDTO
import com.gpluslf.remindme.core.domain.User

fun UserEntity.toUser() = User(
    id,
    username,
    email,
    password,
    salt,
    image?.toUri(),
)

fun UserDTO.toUserEntity() = UserEntity(
    id,
    username,
    email,
    password,
    salt,
    image?.toString(),
)

fun UserEntity.toUserDTO() = UserDTO(
    id,
    username,
    email,
    password,
    salt,
    image.toString(),
)

fun User.toLoggedUserEntity() = UserEntity(
    id,
    username,
    email,
    password,
    salt,
    image?.toString(),
)