package com.gpluslf.remindme.core.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "tasks_tags",
    primaryKeys = ["task_id", "task_list_id", "task_user_id", "tag_id"],
    foreignKeys = [
        ForeignKey(
            entity = TagEntity::class,
            parentColumns = ["id"],
            childColumns = ["tag_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TaskEntity::class,
            parentColumns = ["id", "list_id", "user_id"],
            childColumns = ["task_id", "task_list_id", "task_user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TagsOnTaskEntity(
    @ColumnInfo(name = "task_id")
    val taskId: Long,

    @ColumnInfo(name = "task_list_id")
    val taskListId: Long,

    @ColumnInfo(name = "task_user_id")
    val taskUserId: Long,

    @ColumnInfo(name = "tag_id")
    val tagId: Long,
)
