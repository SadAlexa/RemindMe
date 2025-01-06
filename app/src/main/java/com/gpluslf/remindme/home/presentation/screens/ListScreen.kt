package com.gpluslf.remindme.home.presentation.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gpluslf.remindme.home.presentation.TasksState
import com.gpluslf.remindme.home.presentation.components.CustomTaskItem
import com.gpluslf.remindme.core.presentation.components.NoItemsPlaceholder
import com.gpluslf.remindme.home.presentation.components.sampleTask
import com.gpluslf.remindme.core.presentation.model.toTaskUi
import com.gpluslf.remindme.home.presentation.model.ListScreenAction
import com.gpluslf.remindme.ui.theme.RemindMeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    listTitle: String,
    state: TasksState,
    onFloatingActionButtonClick: () -> Unit,
    onAction: (ListScreenAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = onFloatingActionButtonClick
            ) {
                Icon(Icons.Outlined.Add, "Add Task")
            }
        },
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(horizontal = 30.dp),
                expandedHeight = 80.dp,
                title = {
                    Text(
                        listTitle, style = MaterialTheme.typography.headlineLarge
                    )
                },
                actions = {
                    FilterChip(onClick = {/*TODO*/}, selected = false, label = {
                        Text("All"/*TODO*/, style = MaterialTheme.typography.bodyLarge)
                    })
                }
            )
        }
    ) { contentPadding ->
        if (state.tasks.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.padding(contentPadding).padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                items(state.tasks) { item ->
                    CustomTaskItem(
                        item,
                        onTaskClick = {
                            onAction(ListScreenAction.ToggleTask(item))
                        },
                        onTagClick = {/*TODO*/}
                    )
                }
            }
        } else {
            NoItemsPlaceholder(Modifier.padding(contentPadding), "Tap the + button to add a new task.")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_9_pro")
@Composable
private fun ListScreenPreviewLight() {
    RemindMeTheme {
        Scaffold  { padding ->
            ListScreen(
                listTitle = "List Title",
                state = sampleState,
                {},
                {},
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize())
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_9_pro",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun ListScreenPreviewDark() {
    RemindMeTheme {
        Scaffold  { padding ->
            ListScreen(
                listTitle = "List Title",
                state = sampleState,
                {},
                {},
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize())
        }
    }
}

internal val sampleState = TasksState(
    tasks= (0..10).map { sampleTask.toTaskUi().copy(title = "Task $it") },
)