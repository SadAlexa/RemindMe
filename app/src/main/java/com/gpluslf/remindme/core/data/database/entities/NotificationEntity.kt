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
            parentColumns = ["title","list_title", "user_id"],
            childColumns = ["task_title", "task_list_title", "user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = AchievementEntity::class,
            parentColumns = ["title", "user_id"],
            childColumns = ["achievement_title", "user_id"],
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

    @ColumnInfo(name = "task_title")
    val taskTitle: String?,

    @ColumnInfo(name = "task_list_title")
    val taskListTitle: String?,

    @ColumnInfo(name = "achievement_title")
    val achievementTitle: String?
)

