package com.gpluslf.remindme

import androidx.room.Room
import com.gpluslf.remindme.calendar.presentation.CalendarViewModel
import com.gpluslf.remindme.core.data.database.RemindMeDatabase
import com.gpluslf.remindme.core.data.repository.CategoryRepository
import com.gpluslf.remindme.core.data.repository.ListRepository
import com.gpluslf.remindme.core.data.repository.LoggedUserRepository
import com.gpluslf.remindme.core.data.repository.NotificationRepository
import com.gpluslf.remindme.core.data.repository.TagRepository
import com.gpluslf.remindme.core.data.repository.TaskRepository
import com.gpluslf.remindme.core.data.repository.UserAchievementRepository
import com.gpluslf.remindme.core.data.repository.UserRepository
import com.gpluslf.remindme.core.domain.CategoryDataSource
import com.gpluslf.remindme.core.domain.ListDataSource
import com.gpluslf.remindme.core.domain.LoggedUserDataSource
import com.gpluslf.remindme.core.domain.NotificationDataSource
import com.gpluslf.remindme.core.domain.TagDataSource
import com.gpluslf.remindme.core.domain.TaskDataSource
import com.gpluslf.remindme.core.domain.UserAchievementDataSource
import com.gpluslf.remindme.core.domain.UserDataSource
import com.gpluslf.remindme.home.presentation.AddListViewModel
import com.gpluslf.remindme.home.presentation.AddTaskViewModel
import com.gpluslf.remindme.home.presentation.ListsViewModel
import com.gpluslf.remindme.home.presentation.TasksViewModel
import com.gpluslf.remindme.login.presentation.LoginViewModel
import com.gpluslf.remindme.profile.presentation.UserAchievementViewModel
import com.gpluslf.remindme.profile.presentation.UserViewModel
import com.gpluslf.remindme.updates.presentation.NotificationsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(
            get(),
            RemindMeDatabase::class.java,
            "remindme-database"
        )
            .createFromAsset("database/remindme-database.db")
            .build()
    }

    single { get<RemindMeDatabase>().tagsOnTaskDao() }
    single { get<RemindMeDatabase>().categoryDao() }
    single<UserDataSource> { UserRepository(get<RemindMeDatabase>().userDao()) }
    single<ListDataSource> { ListRepository(get<RemindMeDatabase>().listDao(), get()) }
    single<CategoryDataSource> { CategoryRepository(get()) }
    single<TaskDataSource> { TaskRepository(get<RemindMeDatabase>().taskDao(), get()) }
    single<TagDataSource> { TagRepository(get<RemindMeDatabase>().tagDao()) }
    single<NotificationDataSource> { NotificationRepository(get<RemindMeDatabase>().notificationDao()) }
    single<UserAchievementDataSource> { UserAchievementRepository(get<RemindMeDatabase>().achievementDao()) }
    single<LoggedUserDataSource> { LoggedUserRepository(get<RemindMeDatabase>().loggedUserDao()) }

    viewModel<LoginViewModel> { LoginViewModel(get(), get()) }
    viewModel<UserAchievementViewModel> { (userId: Long) ->
        UserAchievementViewModel(
            userId,
            get()
        )
    }
    viewModel<UserViewModel> { (userId: Long) -> UserViewModel(userId, get(), get()) }
    viewModel<ListsViewModel> { (userId: Long) -> ListsViewModel(userId, get(), get()) }
    viewModel<AddListViewModel> { (userId: Long) -> AddListViewModel(userId, get(), get()) }
    viewModel<TasksViewModel> { (userId: Long, listTitle: String) ->
        TasksViewModel(
            userId,
            listTitle,
            get(),
            get()
        )
    }
    viewModel<AddTaskViewModel> { (userId: Long, listTitle: String) ->
        AddTaskViewModel(
            userId,
            listTitle,
            get(),
            get()
        )
    }
    viewModel<CalendarViewModel> { (userId: Long) -> CalendarViewModel(userId, get()) }
    viewModel<NotificationsViewModel> { (userId: Long) -> NotificationsViewModel(userId, get()) }
}