package com.gpluslf.remindme.core.data.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.gpluslf.remindme.core.data.database.entities.LoggedUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LoggedUserDAOs {
    @Query("SELECT * FROM logged_user LIMIT 1")
    fun getLoggedUserById(): Flow<LoggedUserEntity?>

    @Upsert
    suspend fun upsertLoggedUser(user: LoggedUserEntity)

    @Query("DELETE FROM logged_user")
    suspend fun deleteLoggedUser()

}
