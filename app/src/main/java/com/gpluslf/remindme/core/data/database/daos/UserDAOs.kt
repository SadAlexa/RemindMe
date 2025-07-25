package com.gpluslf.remindme.core.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.gpluslf.remindme.core.data.database.entities.AchievementEntity
import com.gpluslf.remindme.core.data.database.entities.UserAchievementEntity
import com.gpluslf.remindme.core.data.database.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAOs {

    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserById(userId: Long): Flow<UserEntity?>

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun logInUser(email: String): UserEntity?

    @Upsert
    suspend fun upsertUser(user: UserEntity): Long

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Query("UPDATE users SET image = :image WHERE id = :userId")
    suspend fun upsertImage(userId: Long, image: String)


    @Query("SELECT * FROM achievements")
    suspend fun getAllAchievements(): List<AchievementEntity>

    @Insert
    suspend fun insertUserAchievement(achievements: List<UserAchievementEntity>)

    @Transaction
    suspend fun createAccount(user: UserEntity) {
        val userId = upsertUser(user)
        val achievements = getAllAchievements().map { UserAchievementEntity(it.id, userId) }
        insertUserAchievement(achievements)
    }
}