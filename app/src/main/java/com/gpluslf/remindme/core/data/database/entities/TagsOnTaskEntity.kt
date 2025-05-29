package com.gpluslf.remindme.core.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.gpluslf.remindme.core.data.database.DefaultUUID.Companion.DEFAULT_UUID
import java.util.UUID

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
    val taskId: UUID = DEFAULT_UUID,

    @ColumnInfo(name = "task_list_id")
    val taskListId: UUID = DEFAULT_UUID,

    @ColumnInfo(name = "task_user_id")
    val taskUserId: Long,

    @ColumnInfo(name = "tag_id")
    val tagId: UUID = DEFAULT_UUID,
)
