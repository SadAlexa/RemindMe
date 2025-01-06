package com.gpluslf.remindme.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.gpluslf.remindme.calendar.presentation.CalendarViewModel
import com.gpluslf.remindme.calendar.presentation.screens.CalendarScreen
import com.gpluslf.remindme.home.presentation.ListsViewModel
import com.gpluslf.remindme.home.presentation.TaskListViewModel
import com.gpluslf.remindme.home.presentation.TasksViewModel
import com.gpluslf.remindme.home.presentation.TodoListViewModel
import com.gpluslf.remindme.home.presentation.screens.AddListScreen
import com.gpluslf.remindme.home.presentation.screens.AddTaskScreen
import com.gpluslf.remindme.home.presentation.screens.HomeScreen
import com.gpluslf.remindme.home.presentation.screens.ListScreen
import com.gpluslf.remindme.login.presentation.LoginViewModel
import com.gpluslf.remindme.login.presentation.model.LoginAction
import com.gpluslf.remindme.login.presentation.screens.WelcomeScreen
import com.gpluslf.remindme.profile.presentation.AchievementViewModel
import com.gpluslf.remindme.profile.presentation.UserViewModel
import com.gpluslf.remindme.profile.presentation.screens.ProfileScreen
import com.gpluslf.remindme.search.presentation.screens.SearchScreen
import com.gpluslf.remindme.updates.presentation.NotificationsViewModel
import com.gpluslf.remindme.updates.presentation.screens.UpdatesScreen
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

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
    data object AddList : RemindMeRoute

    @Serializable
    data class AddTask( val listTitle: String) : RemindMeRoute

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

    // TODO: add other routes

}

enum class Screens(
    val route: RemindMeRoute,
    val selectedIcon: ImageVector,
    ) {
    Home(RemindMeRoute.Home, Icons.AutoMirrored.Outlined.List),
    Calendar(RemindMeRoute.Calendar, Icons.Outlined.CalendarMonth),
    Profile(RemindMeRoute.Profile, Icons.Outlined.PersonOutline),
    Updates(RemindMeRoute.Updates, Icons.Outlined.Notifications)
}

@Composable
fun RemindMeNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    // TODO: get current user
    val currentUserId = 1L

    NavHost(
        navController = navController,
        startDestination = RemindMeRoute.AUTH,
        modifier = modifier
    ) {
        navigation<RemindMeRoute.AUTH>(
            startDestination = RemindMeRoute.Welcome
        ) {
            composable<RemindMeRoute.Welcome> {
                WelcomeScreen(
                    onLoginAction =  { action ->
                        when(action) {
                            LoginAction.SignIn -> navController.navigate(RemindMeRoute.SignIn)
                            LoginAction.SignUp -> navController.navigate(RemindMeRoute.AddList)
                            LoginAction.Guest -> {
                                navController.popBackStack()
                                navController.navigate(RemindMeRoute.APP)
                            }
                        }
                    }
                )
            }
        }

        navigation<RemindMeRoute.APP>(
            startDestination = RemindMeRoute.Home
        ) {
            composable<RemindMeRoute.Home> {
                val viewModel = koinViewModel<ListsViewModel>(
                    parameters = { parametersOf(currentUserId) }
                )
                val state by viewModel.state.collectAsStateWithLifecycle()
                HomeScreen(
                    state = state,
                    onFloatingActionButtonClick = {
                        navController.navigate(RemindMeRoute.AddList)
                    },
                    onCustomListItemClick = { name ->
                        navController.navigate(RemindMeRoute.TodoList(name))
                    },
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
                    onFloatingActionButtonClick = {
                        navController.navigate(RemindMeRoute.AddTask(listTitle))
                    },
                )
            }
            composable<RemindMeRoute.AddList> {
                val viewModel = koinViewModel<TodoListViewModel>(
                    parameters = { parametersOf(currentUserId) }
                )
                val state by viewModel.state.collectAsStateWithLifecycle()
                AddListScreen(
                    state = state,
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
                val listTitle = it.toRoute<RemindMeRoute.AddTask>().listTitle
                val viewModel = koinViewModel<TaskListViewModel>(
                    parameters = { parametersOf(currentUserId, listTitle) }
                )
                val state by viewModel.state.collectAsStateWithLifecycle()
                AddTaskScreen (
                    state = state,
                    onAddTaskAction = viewModel::onAddTaskAction,
                    onFloatingActionButtonClick = {
                        navController.popBackStack()
                    },
                    onCloseButtonClick = {
                        navController.popBackStack()
                    }
                )
            }
            composable<RemindMeRoute.Calendar>{
                val viewModel = koinViewModel<CalendarViewModel>(
                    parameters = { parametersOf(currentUserId) }
                )
                val state by viewModel.state.collectAsStateWithLifecycle()
                CalendarScreen(
                    taskState = state,
                    viewModel::onAction
                )
            }
            composable<RemindMeRoute.Profile> {
                val userViewModel = koinViewModel<UserViewModel>(
                    parameters = { parametersOf(currentUserId) }
                )
                val achievementViewModel = koinViewModel<AchievementViewModel>(
                    parameters = { parametersOf(currentUserId) }
                )
                val userState by userViewModel.state.collectAsStateWithLifecycle()
                val achievementState by achievementViewModel.state.collectAsStateWithLifecycle()
                ProfileScreen(
                    userState = userState,
                    achievementState = achievementState,
                )
            }
            composable<RemindMeRoute.Updates> {
                val viewModel = koinViewModel<NotificationsViewModel>(
                    parameters = { parametersOf(currentUserId) }
                )
                val state by viewModel.state.collectAsStateWithLifecycle()
                UpdatesScreen(
                    state = state,
                    onNotificationClick = {/*TODO*/}
                )
            }
        }

        //TODO: add other routes
    }
}