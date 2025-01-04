package com.gpluslf.remindme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gpluslf.remindme.core.model.LocationService
import com.gpluslf.remindme.ui.navigation.RemindMeNavGraph
import com.gpluslf.remindme.ui.theme.RemindMeTheme
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RemindMeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val backStackEntry by navController.currentBackStackEntryAsState()
//                    val currentRoute by remember {
//                        derivedStateOf {
//                            RemindMeRoute.routes.find {
//                                it.route == backStackEntry?.destination?.route
//                            } ?: RemindMeRoute.Home
//                        }
//                    }

                    Scaffold(
                        topBar = {} //TODO
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