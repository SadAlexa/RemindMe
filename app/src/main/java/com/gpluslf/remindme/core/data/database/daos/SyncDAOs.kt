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

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUser(userId: Long): UserEntity

    @Query("SELECT * FROM categories WHERE user_id = :userId")
    suspend fun getAllCategories(userId: Long): List<CategoryEntity>

    @Query("SELECT * FROM lists WHERE user_id = :userId")
    suspend fun getAllLists(userId: Long): List<ListEntity>

    @Query("SELECT * FROM tasks WHERE user_id = :userId")
    suspend fun getAllTasks(userId: Long): List<TaskEntity>

    @Query("SELECT * FROM tags WHERE user_id = :userId")
    suspend fun getAllTags(userId: Long): List<TagEntity>

    @Query("SELECT * FROM user_achievements WHERE user_id = :userId")
    suspend fun getAllUserAchievements(userId: Long): List<UserAchievementEntity>

    @Query("SELECT * FROM tasks_tags WHERE task_user_id = :userId")
    suspend fun getAllTagsOnTask(userId: Long): List<TagsOnTaskEntity>

    @Query("SELECT * FROM notifications WHERE user_id = :userId")
    suspend fun getAllNotifications(userId: Long): List<NotificationEntity>

    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUser(userId: Long)

    @Query("DELETE FROM categories WHERE user_id = :userId")
    suspend fun deleteCategories(userId: Long)

    @Query("DELETE FROM lists WHERE user_id = :userId")
    suspend fun deleteLists(userId: Long)

    @Query("DELETE FROM tasks WHERE user_id = :userId")
    suspend fun deleteTasks(userId: Long)

    @Query("DELETE FROM tags WHERE user_id = :userId")
    suspend fun deleteTags(userId: Long)

    @Query("DELETE FROM user_achievements WHERE user_id = :userId")
    suspend fun deleteUserAchievements(userId: Long)

    @Query("DELETE FROM tasks_tags WHERE task_user_id = :userId")
    suspend fun deleteTagsOnTask(userId: Long)

    @Query("DELETE FROM notifications WHERE user_id = :userId")
    suspend fun deleteNotifications(userId: Long)

    @Transaction
    suspend fun sync(
        user: UserEntity,
        categories: List<CategoryEntity>,
        lists: List<ListEntity>,
        tags: List<TagEntity>,
        tasks: List<TaskEntity>,
        tagsOnTask: List<TagsOnTaskEntity>,
        userAchievements: List<UserAchievementEntity>,
        notifications: List<NotificationEntity>
    ) {
        upsertUser(user)
        upsertCategories(categories)
        upsertLists(lists)
        upsertTags(tags)
        upsertTasks(tasks)
        upsertTagsOnTask(tagsOnTask)
        upsertUserAchievements(userAchievements)
        upsertNotifications(notifications)
    }

    @Transaction
    suspend fun deleteSync(userId: Long) {
        deleteNotifications(userId)
        deleteUserAchievements(userId)
        deleteTagsOnTask(userId)
        deleteTasks(userId)
        deleteTags(userId)
        deleteLists(userId)
        deleteCategories(userId)
        deleteUser(userId)
    }
}