package com.gpluslf.remindme.core.data.mappers

import com.gpluslf.remindme.core.data.database.entities.LoggedUserEntity

fun Long.toLoggedUserEntity() = LoggedUserEntity(this)