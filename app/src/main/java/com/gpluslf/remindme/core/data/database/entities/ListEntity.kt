package com.gpluslf.remindme.core.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.gpluslf.remindme.core.data.database.DefaultUUID.Companion.DEFAULT_UUID
import java.util.UUID

@Entity(
    indices = [
        Index(value = ["id", "user_id"], unique = true)
    ],
    tableName = "lists",
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
    @PrimaryKey()
    val id: UUID = DEFAULT_UUID,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "user_id")
    val userId: Long,

    @ColumnInfo(name = "body")
    val body: String? = null,

    @ColumnInfo(name = "image")
    val image: String? = null,

    @ColumnInfo(name = "is_shared")
    val isShared: Boolean = false,

    @ColumnInfo(name = "shared_user_id")
    val sharedUserId: Long? = null,

    @ColumnInfo(name = "category_id")
    val categoryId: String? = null
)