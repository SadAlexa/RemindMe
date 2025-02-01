package com.gpluslf.remindme.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gpluslf.remindme.core.data.database.daos.AchievementDAOs
import com.gpluslf.remindme.core.data.database.daos.CategoryDAOs
import com.gpluslf.remindme.core.data.database.daos.ListDAOs
import com.gpluslf.remindme.core.data.database.daos.LoggedUserDAOs
import com.gpluslf.remindme.core.data.database.daos.NotificationDAOs
import com.gpluslf.remindme.core.data.database.daos.SharedUserListDAOs
import com.gpluslf.remindme.core.data.database.daos.TagDAOs
import com.gpluslf.remindme.core.data.database.daos.TaskDAOs
import com.gpluslf.remindme.core.data.database.daos.UserDAOs
import com.gpluslf.remindme.core.data.database.entities.AchievementEntity
import com.gpluslf.remindme.core.data.database.entities.CategoryEntity
import com.gpluslf.remindme.core.data.database.entities.ListEntity
import com.gpluslf.remindme.core.data.database.entities.LoggedUserEntity
import com.gpluslf.remindme.core.data.database.entities.NotificationEntity
import com.gpluslf.remindme.core.data.database.entities.SharedUserListEntity
import com.gpluslf.remindme.core.data.database.entities.TagEntity
import com.gpluslf.remindme.core.data.database.entities.TagsOnTaskEntity
import com.gpluslf.remindme.core.data.database.entities.TaskEntity
import com.gpluslf.remindme.core.data.database.entities.UserAchievementEntity
import com.gpluslf.remindme.core.data.database.entities.UserEntity
import com.gpluslf.remindme.core.data.database.views.UserAchievementView

@Database(
    entities = [
        UserEntity::class,
        ListEntity::class,
        CategoryEntity::class,
        TaskEntity::class,
        TagEntity::class,
        NotificationEntity::class,
        AchievementEntity::class,
        UserAchievementEntity::class,
        SharedUserListEntity::class,
        TagsOnTaskEntity::class,
        LoggedUserEntity::class,
    ],
    views = [
        UserAchievementView::class
    ],
    version = 1
)
abstract class RemindMeDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAOs
    abstract fun listDao(): ListDAOs
    abstract fun categoryDao(): CategoryDAOs
    abstract fun taskDao(): TaskDAOs
    abstract fun tagDao(): TagDAOs
    abstract fun notificationDao(): NotificationDAOs
    abstract fun achievementDao(): AchievementDAOs
    abstract fun sharedUserListDao(): SharedUserListDAOs
    abstract fun loggedUserDao(): LoggedUserDAOs
}