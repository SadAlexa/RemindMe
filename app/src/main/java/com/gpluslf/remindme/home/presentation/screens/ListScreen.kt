package com.gpluslf.remindme.home.presentation.screens

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Tag
import androidx.compose.material.icons.outlined.TaskAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gpluslf.remindme.R
import com.gpluslf.remindme.core.presentation.components.NoItemsPlaceholder
import com.gpluslf.remindme.core.presentation.components.TaskItem
import com.gpluslf.remindme.core.presentation.components.sampleTask
import com.gpluslf.remindme.home.presentation.TasksState
import com.gpluslf.remindme.home.presentation.components.CustomAlertDialog
import com.gpluslf.remindme.home.presentation.components.CustomModalBottomSheet
import com.gpluslf.remindme.home.presentation.components.FloatingActionAddButton
import com.gpluslf.remindme.home.presentation.components.FloatingActionButtonMenuItem
import com.gpluslf.remindme.home.presentation.components.SwipeToDeleteContainer
import com.gpluslf.remindme.home.presentation.model.ListScreenAction
import com.gpluslf.remindme.home.presentation.model.TagEvent
import com.gpluslf.remindme.ui.theme.RemindMeTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    listTitle: String,
    state: TasksState,
    onAddTaskClick: () -> Unit,
    onAction: (ListScreenAction) -> Unit,
    onEditTaskSwipe: (Long) -> Unit,
    onBackClick: () -> Unit,
    events: Flow<TagEvent> = emptyFlow(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        events.collect {
            when (it) {
                TagEvent.TagCreated -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.tag_created),
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        }
    }

    if (state.showDialog) {
        CustomAlertDialog(
            "Tag",
            state.tagTitle,
            onConfirmation = {
                onAction(ListScreenAction.SaveTag)
            },
            onDismissRequest = {
                onAction(ListScreenAction.ShowDialog(false))
            },
            onTitleValueChange = {
                onAction(ListScreenAction.UpdateTagTitle(it))
            }
        )
    }

    var showBottomSheet by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        CustomModalBottomSheet(
            action = {
                onAction(ListScreenAction.SelectTag(it))
            },
            elements = state.tags,
            key = { it.title },
            onDismissRequest = {
                showBottomSheet = false
            },
            selected = state.selectedTag,
            deleteAction = {
                onAction(ListScreenAction.DeleteTag(it))
            },
            editAction = {
                onAction(ListScreenAction.ShowDialog(true, it))
            }
        )
    }

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionAddButton(
                items = listOf(
                    FloatingActionButtonMenuItem(
                        icon = Icons.Outlined.Tag,
                        text = "Add Tag",
                        onSelected = { onAction(ListScreenAction.ShowDialog(true)) }
                    ),
                    FloatingActionButtonMenuItem(
                        icon = Icons.Outlined.TaskAlt,
                        text = "Add Task",
                        onSelected = onAddTaskClick
                    )
                )
            )
        },
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(end = 30.dp),
                expandedHeight = 80.dp,
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        IconButton(onClick = onBackClick, content = {
                            Icon(
                                Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = "Go back",
                            )
                        })
                        Text(
                            listTitle,
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                actions = {
                    FilterChip(onClick = { showBottomSheet = true }, selected = false, label = {
                        Text(
                            state.selectedTag?.title ?: "All",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    })
                }
            )
        }
    ) { contentPadding ->
        if (state.tasks.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .padding(contentPadding)
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                items(state.tasks, key = { it.id }) { item ->
                    SwipeToDeleteContainer(
                        item,
                        onDelete = {
                            onAction(ListScreenAction.DeleteTask(item))
                        },
                        onEdit = {
                            onAction(ListScreenAction.EditTask(item))
                            onEditTaskSwipe(item.id)
                        },
                        modifier = Modifier.animateItem()
                    ) {
                        TaskItem(
                            item,
                            onCheckClick = {
                                onAction(ListScreenAction.ToggleTask(item))
                            },
                            onTagClick = { tag ->
                                onAction(ListScreenAction.SelectTag(tag))
                            }
                        )
                    }
                }
            }
        } else {
            NoItemsPlaceholder(
                Modifier.padding(contentPadding),
                "Tap the + button to add a new task."
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_9_pro")
@Composable
private fun ListScreenPreviewLight() {
    RemindMeTheme {
        Scaffold { padding ->
            ListScreen(
                listTitle = "List Title",
                state = sampleState,
                {},
                {},
                {},
                onBackClick = {},
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            )
        }
    }
}

@Preview(
    showBackground = true, showSystemUi = true, device = "id:pixel_9_pro",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun ListScreenPreviewDark() {
    RemindMeTheme {
        Scaffold { padding ->
            ListScreen(
                listTitle = "List Title",
                state = sampleState,
                {},
                {},
                {},
                onBackClick = {},
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            )
        }
    }
}

internal val sampleState = TasksState(
    tasks = (0..10).map { sampleTask.copy(title = "Task $it") },
)