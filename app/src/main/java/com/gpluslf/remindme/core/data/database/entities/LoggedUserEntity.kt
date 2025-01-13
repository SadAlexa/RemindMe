package com.gpluslf.remindme.core.data.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "logged_user")
data class LoggedUserEntity(
    @PrimaryKey
    val id: Long
)