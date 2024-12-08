package com.gpluslf.remindme.core.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "tasks_tags",
    primaryKeys = ["task_title", "task_list_title", "task_user_id", "tag_id"],
    foreignKeys = [
        ForeignKey(
            entity = TagEntity::class,
            parentColumns = ["id"],
            childColumns = ["tag_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TaskEntity::class,
            parentColumns = ["title", "list_title", "user_id"],
            childColumns = ["task_title", "task_list_title", "task_user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TagsOnTaskEntity(
    @ColumnInfo(name = "task_title")
    val taskTitle: String,

    @ColumnInfo(name = "task_list_title")
    val taskListTitle: String,

    @ColumnInfo(name = "task_user_id")
    val taskUserId: Long,

    @ColumnInfo(name = "tag_id")
    val tagId: Long,
)
