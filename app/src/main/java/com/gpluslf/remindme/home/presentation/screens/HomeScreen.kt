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
import com.gpluslf.remindme.home.presentation.ListsState
import com.gpluslf.remindme.core.presentation.components.NoItemsPlaceholder
import com.gpluslf.remindme.home.presentation.components.CustomListItem
import com.gpluslf.remindme.home.presentation.components.sampleTodoList
import com.gpluslf.remindme.core.presentation.model.toTodoListUi
import com.gpluslf.remindme.ui.theme.RemindMeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: ListsState,
    onFloatingActionButtonClick: () -> Unit,
    onCustomListItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = onFloatingActionButtonClick
            ) {
                Icon(Icons.Outlined.Add, "Add List")
            }
        },
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(horizontal = 30.dp),
                expandedHeight = 80.dp,
                title = {
                    Text(
                    "Your Lists", style = MaterialTheme.typography.headlineLarge
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
        if (state.lists.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.padding(contentPadding).padding(horizontal = 20.dp),
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
            NoItemsPlaceholder(Modifier.padding(contentPadding), "Tap the + button to add a new list.")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_9_pro")
@Composable
private fun HomeScreenPreviewLight() {
    RemindMeTheme {
        Scaffold  { padding ->
            HomeScreen(
                state = ListsState(
                    lists = (0..10).map { sampleTodoList.toTodoListUi().copy(title = "List $it") }
                ),
                {},
                {_ ->},
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
private fun HomeScreenPreviewDark() {
    RemindMeTheme {
        Scaffold  { padding ->
            HomeScreen(
                state = ListsState(
                    lists = (0..10).map { sampleTodoList.toTodoListUi().copy(title = "List $it") }
                ),
                {},
                {_ ->},
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize())
        }
    }
}