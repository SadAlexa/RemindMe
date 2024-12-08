package com.gpluslf.remindme.core.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tags",
    foreignKeys = [
        ForeignKey(
            entity = ListEntity::class,
            parentColumns = ["user_id", "title"],
            childColumns = ["user_id", "list_title"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "list_title")
    val listTitle: String,

    @ColumnInfo(name = "user_id")
    val userId: Long
)