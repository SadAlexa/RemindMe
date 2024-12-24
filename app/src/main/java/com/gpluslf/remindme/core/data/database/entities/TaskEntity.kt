package com.gpluslf.remindme.core.data.database.entities

import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.ForeignKey
import java.util.Date

@Entity(
    tableName = "tasks",
    primaryKeys = ["title", "list_title", "user_id"],
    foreignKeys = [
        ForeignKey(
            entity = ListEntity::class,
            parentColumns = ["title", "user_id"],
            childColumns = ["list_title", "user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class TaskEntity(
    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "list_title")
    val listTitle: String,

    @ColumnInfo(name = "user_id")
    val userId: Long,

    @ColumnInfo(name = "body")
    val body: String? = null,

    @ColumnInfo(name = "end_time")
    val endTime: Date? = null,

    @ColumnInfo(name = "frequency")
    val frequency: Date? = null,

    @ColumnInfo(name = "alert")
    val alert: Date? = null,

    @ColumnInfo(name = "image")
    val image: ByteArray? = null
)

