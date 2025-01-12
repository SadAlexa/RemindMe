package com.gpluslf.remindme.home.presentation.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gpluslf.remindme.core.presentation.components.NoItemsPlaceholder
import com.gpluslf.remindme.core.presentation.model.toTodoListUi
import com.gpluslf.remindme.home.presentation.ListsState
import com.gpluslf.remindme.home.presentation.components.CustomAlertDialog
import com.gpluslf.remindme.home.presentation.components.CustomListItem
import com.gpluslf.remindme.home.presentation.components.CustomModalBottomSheet
import com.gpluslf.remindme.home.presentation.components.FloatingActionAddButton
import com.gpluslf.remindme.home.presentation.components.FloatingActionButtonMenuItem
import com.gpluslf.remindme.home.presentation.components.sampleTodoList
import com.gpluslf.remindme.home.presentation.model.CategoryUi
import com.gpluslf.remindme.home.presentation.model.HomeScreenAction
import com.gpluslf.remindme.ui.theme.RemindMeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: ListsState,
    onHomeScreenAction: (HomeScreenAction) -> Unit,
    onAddListClick: () -> Unit,
    onCustomListItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    if (state.showDialog) {
        CustomAlertDialog(
            "Category",
            state.categoryTitle,
            onConfirmation = {
                onHomeScreenAction(HomeScreenAction.SaveCategory)
            },
            onDismissRequest = {
                onHomeScreenAction(HomeScreenAction.ShowDialog(false))
            },
            onTitleValueChange = {
                onHomeScreenAction(HomeScreenAction.UpdateCategoryTitle(it))
            }
        )
    }

    var showBottomSheet by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        CustomModalBottomSheet(
            action = {
                onHomeScreenAction(HomeScreenAction.SetCategory(it))
            },
            elements = state.categories,
            key = { it.title },
            onDismissRequest = {
                showBottomSheet = false
            },
            selected = state.selectedCategory
        )
    }

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionAddButton(
                items = listOf(
                    FloatingActionButtonMenuItem(
                        icon = Icons.Outlined.Category,
                        text = "Category",
                        onSelected = {
                            onHomeScreenAction(HomeScreenAction.ShowDialog(true))
                        }
                    ),
                    FloatingActionButtonMenuItem(
                        icon = Icons.AutoMirrored.Outlined.List,
                        text = "List",
                        onSelected = onAddListClick
                    )
                )
            )
        },
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(horizontal = 30.dp),
                expandedHeight = 80.dp,
                title = {
                    Text(
                        "Your Lists",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    FilterChip(onClick = { showBottomSheet = true }, selected = false, label = {
                        Text(
                            state.selectedCategory?.title ?: "All",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    })
                }
            )
        }
    ) { contentPadding ->
        if (state.lists.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .padding(contentPadding)
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                items(state.lists) { item ->
                    CustomListItem(
                        item,
                        onClick = {
                            onCustomListItemClick(item.title)
                        }
                    )
                }
            }
        } else {
            NoItemsPlaceholder(
                Modifier.padding(contentPadding),
                "Tap the + button to add a new list."
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_9_pro")
@Composable
private fun HomeScreenPreviewLight() {
    RemindMeTheme {
        Scaffold { padding ->
            HomeScreen(
                state = ListsState(
                    lists = (0..10).map { sampleTodoList.toTodoListUi().copy(title = "List $it") },
                ),
                {},
                {},
                {},
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
private fun HomeScreenPreviewDark() {
    RemindMeTheme {
        Scaffold { padding ->
            HomeScreen(
                state = ListsState(
                    lists = (0..10).map { sampleTodoList.toTodoListUi().copy(title = "List $it") }
                ),
                {},
                {},
                {},
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            )
        }
    }
}