package com.gpluslf.remindme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gpluslf.remindme.core.domain.DataStoreSource
import com.gpluslf.remindme.ui.navigation.RemindMeNavGraph
import com.gpluslf.remindme.ui.navigation.Screens
import com.gpluslf.remindme.ui.theme.RemindMeTheme
import org.koin.android.ext.android.getKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkTheme: Boolean? by remember { mutableStateOf(null) }
            RemindMeTheme(darkTheme = if (isDarkTheme != null) isDarkTheme!! else isSystemInDarkTheme()) {
                LaunchedEffect(Unit) {
                    getKoin().get<DataStoreSource>().getInt("theme").collect {
                        isDarkTheme = it == 2
                    }
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val backStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = backStackEntry?.destination
                    val showBottomBar = Screens.entries.any { screen ->
                        currentDestination?.hierarchy?.any {
                            it.hasRoute(route = screen.route::class)
                        } ?: false
                    }

                    Scaffold(
                        bottomBar = {
                            AnimatedVisibility(showBottomBar) {
                                NavigationBar {
                                    Screens.entries.forEach { currentScreen ->
                                        val isSelected =
                                            currentDestination?.hierarchy?.any {
                                                it.hasRoute(
                                                    currentScreen.route::class
                                                )
                                            } == true
                                        NavigationBarItem(
                                            selected = isSelected,
                                            onClick = {
                                                navController.navigate(currentScreen.route) {
                                                    popUpTo(navController.graph.findStartDestination().id) {
                                                        saveState = true
                                                    }
                                                    launchSingleTop = true
                                                    restoreState = true
                                                }
                                            },
                                            icon = { Icon(currentScreen.selectedIcon, null) },
                                        )
                                    }
                                }
                            }
                        }
                    ) { innerPadding ->
                        RemindMeNavGraph(
                            navController,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}