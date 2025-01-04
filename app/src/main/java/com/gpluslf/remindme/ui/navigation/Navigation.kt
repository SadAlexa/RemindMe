package com.gpluslf.remindme.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.gpluslf.remindme.home.presentation.ListsViewModel
import com.gpluslf.remindme.home.presentation.TasksViewModel
import com.gpluslf.remindme.home.presentation.TodoListViewModel
import com.gpluslf.remindme.home.presentation.screens.AddListScreen
import com.gpluslf.remindme.home.presentation.screens.HomeScreen
import com.gpluslf.remindme.home.presentation.screens.ListScreen
import com.gpluslf.remindme.login.presentation.LoginViewModel
import com.gpluslf.remindme.login.presentation.model.LoginAction
import com.gpluslf.remindme.login.presentation.screens.WelcomeScreen
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

//sealed class RemindMeRoute(
//    val route: String,
//    val title: String,
//    val arguments: List<NamedNavArgument> = emptyList()
//) {
//    data object Home : RemindMeRoute("lists", "Lists")
//    data object TodoList : RemindMeRoute(
//        "lists/{listTitle}",
//        "{listTitle}",
//        listOf(navArgument("listTitle") { type = NavType.StringType })
//    ) {
//        fun buildRoute(listTitle: String) = "lists/$listTitle"
//    }
//    data object AddList : RemindMeRoute("add_list", "Add List")
//    // TODO: add other routes
//    companion object {
//        val routes = setOf(Home, TodoList)
//    }
//}

sealed interface RemindMeRoute {

    @Serializable
    data object Welcome

    @Serializable
    data object Home

    @Serializable
    data object SignIn

    @Serializable
    data object SignUp

    @Serializable
    data class TodoList(val listTitle: String)

    @Serializable
    data object AddList

    @Serializable
    data class AddTask( val listTitle: String)

    @Serializable
    data object AUTH

    @Serializable
    data object APP

    // TODO: add other routes

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
                    onCustomTaskClick = {/*TODO*/},
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
        }

        //TODO: add other routes
    }
}