package com.gpluslf.remindme.core.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.gpluslf.remindme.core.data.database.DefaultUUID.Companion.DEFAULT_UUID
import java.util.UUID

@Entity(
    tableName = "tags",
    foreignKeys = [
        ForeignKey(
            entity = ListEntity::class,
            parentColumns = ["user_id", "id"],
            childColumns = ["user_id", "list_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TagEntity(
    @PrimaryKey()
    val id: UUID = DEFAULT_UUID,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "list_id")
    val listId: UUID = DEFAULT_UUID,

    @ColumnInfo(name = "user_id")
    val userId: Long
)