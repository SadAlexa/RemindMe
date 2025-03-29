package com.gpluslf.remindme.core.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "notifications",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TaskEntity::class,
            parentColumns = ["id", "list_id", "user_id"],
            childColumns = ["task_id", "task_list_id", "user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = UserAchievementEntity::class,
            parentColumns = ["achievement_id", "user_id"],
            childColumns = ["achievement_id", "user_id"],
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class NotificationEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "body")
    val body: String,

    @ColumnInfo(name = "user_id")
    val userId: Long,

    @ColumnInfo(name = "sender_user_id")
    val senderUserId: Long?,

    @ColumnInfo(name = "send_time")
    val sendTime: Long,

    @ColumnInfo(name = "is_read")
    val isRead: Boolean,

    @ColumnInfo(name = "task_id")
    val taskId: Long?,

    @ColumnInfo(name = "task_title")
    val taskTitle: String?,

    @ColumnInfo(name = "task_list_id")
    val taskListId: Long?,

    @ColumnInfo(name = "achievement_id")
    val achievementId: Long?
)

