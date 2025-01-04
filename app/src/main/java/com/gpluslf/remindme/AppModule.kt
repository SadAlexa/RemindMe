package com.gpluslf.remindme

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
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
import com.gpluslf.remindme.core.data.mappers.toUserEntity
import com.gpluslf.remindme.core.domain.CategoryDataSource
import com.gpluslf.remindme.core.domain.ListDataSource
import com.gpluslf.remindme.core.domain.NotificationDataSource
import com.gpluslf.remindme.core.domain.TagDataSource
import com.gpluslf.remindme.core.domain.TaskDataSource
import com.gpluslf.remindme.core.domain.User
import com.gpluslf.remindme.core.domain.UserDataSource
import com.gpluslf.remindme.home.presentation.ListsViewModel
import com.gpluslf.remindme.home.presentation.TasksViewModel
import com.gpluslf.remindme.home.presentation.TodoListViewModel
import com.gpluslf.remindme.login.presentation.LoginViewModel
import io.ktor.http.parametersOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.module.dsl.viewModel
import org.koin.core.scope.Scope
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            get(),
            RemindMeDatabase::class.java,
            "remindme-database"
        ).addCallback(
            object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    val scope = CoroutineScope(Dispatchers.IO)
                    val database = get<RemindMeDatabase>()
                    val userDao = database.userDao()
                    val user = User(
                        id = 1,
                        username = "guest",
                        name = "Guest",
                        email = "guest",
                        password = "guest",
                        salt = "guest",
                        image = null
                    )
                    scope.launch {
                        userDao.upsertUser(user.toUserEntity())
                    }
                    super.onCreate(db)
                }
            }
        ).build()
    }
    single { get<RemindMeDatabase>().tagsOnTaskDao() }
    single { get<RemindMeDatabase>().categoryDao() }
    single<UserDataSource> { UserRepository(get<RemindMeDatabase>().userDao()) }
    single<ListDataSource> { ListRepository(get<RemindMeDatabase>().listDao(), get()) }
    single<CategoryDataSource> { CategoryRepository(get()) }
    single<TaskDataSource> { TaskRepository(get<RemindMeDatabase>().taskDao(), get())}
    single<TagDataSource> { TagRepository(get<RemindMeDatabase>().tagDao()) }
    single<NotificationDataSource> { NotificationRepository(get<RemindMeDatabase>().notificationDao()) }
    // single { AchievementRepository(get<RemindMeDatabase>().achievementDao()) }
    // single { SharedUserListRepository(get<RemindMeDatabase>().sharedUserListDao()) }
    // single { TagsOnTaskRepository(get()) }

    viewModel<LoginViewModel> { LoginViewModel() }
    viewModel<ListsViewModel> { (userId: Long) -> ListsViewModel(userId, get()) }
    viewModel<TodoListViewModel> { (userId: Long) -> TodoListViewModel(userId, get()) }
    viewModel<TasksViewModel> { (userId: Long, listTitle: String) -> TasksViewModel(userId, listTitle, get()) }
}