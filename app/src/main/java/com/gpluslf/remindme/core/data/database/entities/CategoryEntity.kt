package com.gpluslf.remindme.core.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.gpluslf.remindme.core.data.database.DefaultUUID.Companion.DEFAULT_UUID
import java.util.UUID

@Entity(
    tableName = "categories",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE // Action on delete
        )
    ]
)
data class CategoryEntity(
    @PrimaryKey()
    val id: UUID = DEFAULT_UUID,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "user_id")
    val userId: Long
)
