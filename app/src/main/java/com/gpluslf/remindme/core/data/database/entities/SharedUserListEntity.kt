package com.gpluslf.remindme.core.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "shared_user_list",
    primaryKeys = ["user_id", "list_shared_user_id", "list_title"],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["list_shared_user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ListEntity::class,
            parentColumns = ["title", "user_id"],
            childColumns = ["list_title", "user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SharedUserListEntity(

    @ColumnInfo(name = "user_id")
    val usersId: Long,

    @ColumnInfo(name = "list_shared_user_id")
    val listsSharedUserId: Long,

    @ColumnInfo(name = "list_title")
    val listTitle: String
)
