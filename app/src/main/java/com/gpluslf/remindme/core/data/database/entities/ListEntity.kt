package com.gpluslf.remindme.core.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "lists",
    primaryKeys = ["title", "user_id"],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE // Action on delete
        ),
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.CASCADE // Action on delete
        ),
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["shared_user_id"],
            onDelete = ForeignKey.SET_NULL // Action on delete
        )
    ]
)
data class ListEntity(
    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "user_id")
    val userId: Long,

    @ColumnInfo(name = "body")
    val body: String? = null,

    @ColumnInfo(name = "image")
    val image: ByteArray? = null,

    @ColumnInfo(name = "is_shared")
    val isShared: Boolean = false,

    @ColumnInfo(name = "shared_user_id")
    val sharedUserId: Long? = null,

    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false,

    @ColumnInfo(name = "category_id")
    val categoryId: Long? = null
)