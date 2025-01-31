package com.gpluslf.remindme.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.gpluslf.remindme.calendar.presentation.CalendarViewModel
import com.gpluslf.remindme.calendar.presentation.screens.CalendarScreen
import com.gpluslf.remindme.core.domain.AlarmScheduler
import com.gpluslf.remindme.core.domain.LoggedUserDataSource
import com.gpluslf.remindme.core.domain.Notification
import com.gpluslf.remindme.core.domain.UserAchievementDataSource
import com.gpluslf.remindme.home.presentation.AddListViewModel
import com.gpluslf.remindme.home.presentation.AddTaskViewModel
import com.gpluslf.remindme.home.presentation.ListsViewModel
import com.gpluslf.remindme.home.presentation.TasksViewModel
import com.gpluslf.remindme.home.presentation.screens.AddListScreen
import com.gpluslf.remindme.home.presentation.screens.AddTaskScreen
import com.gpluslf.remindme.home.presentation.screens.HomeScreen
import com.gpluslf.remindme.home.presentation.screens.ListScreen
import com.gpluslf.remindme.login.presentation.LoginViewModel
import com.gpluslf.remindme.login.presentation.model.LoginAction
import com.gpluslf.remindme.login.presentation.screens.SignInScreen
import com.gpluslf.remindme.login.presentation.screens.SignUpScreen
import com.gpluslf.remindme.login.presentation.screens.WelcomeScreen
import com.gpluslf.remindme.profile.presentation.SettingsViewModel
import com.gpluslf.remindme.profile.presentation.UserAchievementViewModel
import com.gpluslf.remindme.profile.presentation.UserViewModel
import com.gpluslf.remindme.profile.presentation.model.ProfileAction
import com.gpluslf.remindme.profile.presentation.screens.ProfileScreen
import com.gpluslf.remindme.updates.presentation.NotificationsState
import com.gpluslf.remindme.updates.presentation.NotificationsViewModel
import com.gpluslf.remindme.updates.presentation.screens.UpdatesScreen
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.getKoin
import org.koin.core.parameter.parametersOf
import java.util.Date

sealed interface RemindMeRoute {

    @Serializable
    data object Welcome : RemindMeRoute

    @Serializable
    data object Home : RemindMeRoute

    @Serializable
    data object SignIn : RemindMeRoute

    @Serializable
    data object SignUp : RemindMeRoute

    @Serializable
    data class TodoList(val listTitle: String) : RemindMeRoute

    @Serializable
    data class AddList(val listTitle: String? = null) : RemindMeRoute

    @Serializable
    data class AddTask(val listTitle: String, val taskTitle: String? = null) : RemindMeRoute

    @Serializable
    data object Calendar : RemindMeRoute

    @Serializable
    data object Profile : RemindMeRoute

    @Serializable
    data object Search : RemindMeRoute

    @Serializable
    data object Updates : RemindMeRoute

    @Serializable
    data object AUTH : RemindMeRoute

    @Serializable
    data object APP : RemindMeRoute

}

enum class Screens(
    val route: RemindMeRoute,
    val selectedIcon: ImageVector
) {
    Home(RemindMeRoute.Home, Icons.AutoMirrored.Outlined.List),
    Calendar(RemindMeRoute.Calendar, Icons.Outlined.CalendarMonth),
    Profile(RemindMeRoute.Profile, Icons.Outlined.PersonOutline),
    Updates(RemindMeRoute.Updates, Icons.Outlined.Notifications)
}

@Composable
fun RemindMeNavGraph(
    navController: NavHostController,
    goToUpdates: Boolean = false,
    updateBadgeCount: (Screens, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    var currentUserId: Long? by remember {
        mutableStateOf(null)
    }

    var flag by remember { mutableStateOf(false) }

    val loggedUserRepository = getKoin().get<LoggedUserDataSource>()
    val achievementRepository = getKoin().get<UserAchievementDataSource>()
    val alarmScheduler = getKoin().get<AlarmScheduler>()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            loggedUserRepository.getLoggedUserById().collect { id ->
                currentUserId = id
            }
        }
    }
    LaunchedEffect(currentUserId) {
        val user = currentUserId
        if (user != null) {
            coroutineScope.launch {
                achievementRepository.getAllUnnotifiedAchievements(user)
                    .collect { achievements ->
                        achievements.forEach { achievement ->
                            val scheduleId =
                                "$currentUserId/${achievement.achievementId}/${achievement.achievementTitle}".hashCode()
                                    .toLong()
                            val notificationItem = Notification(
                                id = scheduleId,
                                sendTime = Date(),
                                userId = user,
                                body = "You have unlocked the ${achievement.achievementTitle}",
                                title = "ACHIEVEMENT UNLOCKED!",
                                achievementId = achievement.achievementId
                            )
                            alarmScheduler.schedule(notificationItem)
                        }

                        achievementRepository.updateUserAchievement(achievements.map {
                            it.copy(
                                isNotified = true
                            )
                        })
                    }
            }


        }
    }

    lateinit var notificationsViewModel: NotificationsViewModel
    var notificationsState by remember { mutableStateOf(NotificationsState()) }
    if (currentUserId != null) {
        notificationsViewModel = koinViewModel<NotificationsViewModel>(
            parameters = { parametersOf(currentUserId) }
        )
        notificationsState = notificationsViewModel.state.collectAsStateWithLifecycle().value
        LaunchedEffect(notificationsState.notifications) {
            updateBadgeCount(
                Screens.Updates,
                notificationsState.notifications.filterNot { it.isRead }.size
            )
        }
    }



    NavHost(
        navController = navController,
        startDestination = if (currentUserId != null) RemindMeRoute.APP else RemindMeRoute.AUTH,
        modifier = modifier
    ) {
        navigation<RemindMeRoute.AUTH>(
            startDestination = RemindMeRoute.Welcome
        ) {
            composable<RemindMeRoute.Welcome> {
                WelcomeScreen(
                    onLoginAction = { action ->
                        when (action) {
                            LoginAction.SignIn -> navController.navigate(RemindMeRoute.SignIn)
                            LoginAction.SignUp -> navController.navigate(RemindMeRoute.SignUp)
                            LoginAction.Guest -> {
                                coroutineScope.launch {
                                    loggedUserRepository.upsertLoggedUser(1)
                                }
                                navController.popBackStack()
                                navController.navigate(RemindMeRoute.APP)
                            }
                        }
                    }
                )
            }

            composable<RemindMeRoute.SignIn> {
                val viewModel = koinViewModel<LoginViewModel>()
                val state by viewModel.signInState.collectAsStateWithLifecycle()

                LaunchedEffect(state.isLoggedIn) {
                    if (state.isLoggedIn) {
                        navController.popBackStack(
                            route = RemindMeRoute.AUTH,
                            inclusive = false
                        )
                        navController.navigate(RemindMeRoute.APP)
                    }
                }
                SignInScreen(
                    state = state,
                    onSignInAction = viewModel::onSignInAction,
                    onLoginAction = viewModel::onLoginAction,
                    events = viewModel.events
                )
            }

            composable<RemindMeRoute.SignUp> {
                val viewModel = koinViewModel<LoginViewModel>()
                val state by viewModel.signUpState.collectAsStateWithLifecycle()
                SignUpScreen(
                    state = state,
                    onSignUpAction = viewModel::onSignUpAction,
                    onLoginAction = { action ->
                        viewModel.onLoginAction(action)
                        if (action == LoginAction.SignUp) {
                            navController.popBackStack()
                        }
                    }
                )
            }
        }

        navigation<RemindMeRoute.APP>(
            startDestination = RemindMeRoute.Home
        ) {
            composable<RemindMeRoute.Home> {
                LaunchedEffect(Unit) {
                    if (goToUpdates && !flag) {
                        navController.navigate(RemindMeRoute.Updates) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        flag = true
                    }
                }

                val viewModel = koinViewModel<ListsViewModel>(
                    parameters = { parametersOf(currentUserId) }
                )
                val state by viewModel.state.collectAsStateWithLifecycle()
                HomeScreen(
                    state = state,
                    onHomeScreenAction = viewModel::onHomeScreenAction,
                    onAddListClick = {
                        navController.navigate(RemindMeRoute.AddList())
                    },
                    onEditListSwipe = { name ->
                        navController.navigate(RemindMeRoute.AddList(name))
                    },
                    onCustomListItemClick = { name ->
                        navController.navigate(RemindMeRoute.TodoList(name))
                    }
                )
            }
            composable<RemindMeRoute.TodoList> {
                val listTitle = it.toRoute<RemindMeRoute.TodoList>().listTitle
                val viewModel = koinViewModel<TasksViewModel>(
                    parameters = { parametersOf(currentUserId, listTitle) }
                )
                val state by viewModel.state.collectAsStateWithLifecycle()
                ListScreen(
                    listTitle = listTitle,
                    state = state,
                    onAction = viewModel::onAction,
                    onAddTaskClick = {
                        navController.navigate(RemindMeRoute.AddTask(listTitle))
                    },
                    onEditTaskSwipe = { name ->
                        navController.navigate(RemindMeRoute.AddTask(listTitle, name))
                    },
                    onBackClick = {
                        navController.popBackStack()
                    },
                )
            }
            composable<RemindMeRoute.AddList> {
                val listTitle = it.toRoute<RemindMeRoute.AddList>().listTitle
                val viewModel = koinViewModel<AddListViewModel>(
                    parameters = { parametersOf(currentUserId, listTitle) }
                )
                val state by viewModel.state.collectAsStateWithLifecycle()
                AddListScreen(
                    state = state,
                    isNew = listTitle == null,
                    onAddListAction = viewModel::onAddListAction,
                    onFloatingActionButtonClick = {
                        navController.navigate(RemindMeRoute.Home)
                    },
                    onCloseActionButtonClick = {
                        navController.popBackStack()
                    }
                )
            }
            composable<RemindMeRoute.AddTask> {
                val addTask = it.toRoute<RemindMeRoute.AddTask>()
                val viewModel = koinViewModel<AddTaskViewModel>(
                    parameters = {
                        parametersOf(
                            currentUserId,
                            addTask.listTitle,
                            addTask.taskTitle,
                        )
                    }
                )

                val state by viewModel.state.collectAsStateWithLifecycle()
                AddTaskScreen(
                    state = state,
                    isNew = addTask.taskTitle == null,
                    onAddTaskAction = viewModel::onAddTaskAction,
                    onBack = {
                        navController.popBackStack()
                    },
                )
            }
            composable<RemindMeRoute.Calendar> {
                val viewModel = koinViewModel<CalendarViewModel>(
                    parameters = { parametersOf(currentUserId) }
                )
                val state by viewModel.state.collectAsStateWithLifecycle()
                CalendarScreen(
                    taskState = state,
                    viewModel::onAction,
                    viewModel::hasEvents
                )
            }
            composable<RemindMeRoute.Profile> {
                val userViewModel = koinViewModel<UserViewModel>(
                    parameters = { parametersOf(currentUserId) }
                )
                val achievementViewModel = koinViewModel<UserAchievementViewModel>(
                    parameters = { parametersOf(currentUserId) }
                )
                val settingsViewModel = koinViewModel<SettingsViewModel>()
                val userState by userViewModel.state.collectAsStateWithLifecycle()
                val achievementState by achievementViewModel.state.collectAsStateWithLifecycle()
                val settingsState by settingsViewModel.state.collectAsStateWithLifecycle()
                ProfileScreen(
                    userState = userState,
                    settingState = settingsState,
                    onProfileAction = { action ->
                        userViewModel.onProfileAction(action)
                        if (action == ProfileAction.LogOut) {
                            navController.popBackStack(
                                route = RemindMeRoute.AUTH,
                                inclusive = false
                            )
                        }
                    },
                    onSettingAction = settingsViewModel::onAction,
                    userAchievementState = achievementState,
                )
            }
            composable<RemindMeRoute.Updates> {
                UpdatesScreen(
                    state = notificationsState,
                    onNotificationAction = notificationsViewModel::onAction,
                    onTaskNotificationClick = {
                        navController.navigate(RemindMeRoute.TodoList(it))
                    },
                    onAchievementNotificationClick = {
                        navController.navigate(RemindMeRoute.Profile) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            }
        }
    }
}