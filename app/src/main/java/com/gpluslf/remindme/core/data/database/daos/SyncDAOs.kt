package com.gpluslf.remindme.core.data.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.gpluslf.remindme.core.data.database.entities.CategoryEntity
import com.gpluslf.remindme.core.data.database.entities.ListEntity
import com.gpluslf.remindme.core.data.database.entities.NotificationEntity
import com.gpluslf.remindme.core.data.database.entities.TagEntity
import com.gpluslf.remindme.core.data.database.entities.TagsOnTaskEntity
import com.gpluslf.remindme.core.data.database.entities.TaskEntity
import com.gpluslf.remindme.core.data.database.entities.UserAchievementEntity
import com.gpluslf.remindme.core.data.database.entities.UserEntity

@Dao
interface SyncDAOs {

    @Upsert
    suspend fun upsertUser(user: UserEntity)

    @Upsert
    suspend fun upsertCategories(categories: List<CategoryEntity>)

    @Upsert
    suspend fun upsertLists(lists: List<ListEntity>)

    @Upsert
    suspend fun upsertTags(tags: List<TagEntity>)

    @Upsert
    suspend fun upsertTasks(tasks: List<TaskEntity>)

    @Upsert
    suspend fun upsertTagsOnTask(tagsOnTask: List<TagsOnTaskEntity>)

    @Upsert
    suspend fun upsertUserAchievements(userAchievements: List<UserAchievementEntity>)

    @Upsert
    suspend fun upsertNotifications(notifications: List<NotificationEntity>)

    @Query("SELECT * FROM categories WHERE user_id = :userId")
    suspend fun getAllCategories(userId: Long): List<CategoryEntity>

    @Transaction
    suspend fun sync(
        user: UserEntity,
        categories: List<CategoryEntity>,
//        lists: List<ListEntity>,
//        tags: List<TagEntity>,
//        tasks: List<TaskEntity>,
//        tagsOnTask: List<TagsOnTaskEntity>,
//        userAchievements: List<UserAchievementEntity>,
//        notifications: List<NotificationEntity>
    ) {
        upsertUser(user)
        upsertCategories(categories)
//        upsertLists(lists)
//        upsertTags(tags)
//        upsertTasks(tasks)
//        upsertTagsOnTask(tagsOnTask)
//        upsertUserAchievements(userAchievements)
//        upsertNotifications(notifications)
    }


}