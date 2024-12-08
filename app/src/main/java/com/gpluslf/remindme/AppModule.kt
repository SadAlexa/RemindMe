package com.gpluslf.remindme

import androidx.room.Room
import com.gpluslf.remindme.core.data.database.repository.AchievementRepository
import com.gpluslf.remindme.core.data.database.repository.CategoryRepository
import com.gpluslf.remindme.core.data.database.RemindMeDatabase
import com.gpluslf.remindme.core.data.database.repository.ListRepository
import com.gpluslf.remindme.core.data.database.repository.NotificationRepository
import com.gpluslf.remindme.core.data.database.repository.SharedUserListRepository
import com.gpluslf.remindme.core.data.database.repository.TagsOnTaskRepository
import com.gpluslf.remindme.core.data.database.repository.TagRepository
import com.gpluslf.remindme.core.data.database.repository.TaskRepository
import com.gpluslf.remindme.core.data.database.repository.UserRepository
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            get(),
            RemindMeDatabase::class.java,
            "remindme-database"
        ).build()
    }
    single { UserRepository(get<RemindMeDatabase>().userDao()) }
    single { ListRepository(get<RemindMeDatabase>().listDao()) }
    single { CategoryRepository(get<RemindMeDatabase>().categoryDao()) }
    single { TaskRepository(get<RemindMeDatabase>().taskDao()) }
    single { TagRepository(get<RemindMeDatabase>().tagDao()) }
    single { NotificationRepository(get<RemindMeDatabase>().notificationDao()) }
    single { AchievementRepository(get<RemindMeDatabase>().achievementDao()) }
    single { SharedUserListRepository(get<RemindMeDatabase>().sharedUserListDao()) }
    single { TagsOnTaskRepository(get<RemindMeDatabase>().tagsOnTaskDao()) }
}