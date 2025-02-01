package com.gpluslf.remindme.home.presentation.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.gpluslf.remindme.R
import com.gpluslf.remindme.core.domain.Category
import com.gpluslf.remindme.core.presentation.components.ImagePickerBottomSheet
import com.gpluslf.remindme.home.presentation.components.CustomPhotoButton
import com.gpluslf.remindme.home.presentation.components.CustomTextField
import com.gpluslf.remindme.home.presentation.model.AddListAction
import com.gpluslf.remindme.home.presentation.model.TodoListState
import com.gpluslf.remindme.home.presentation.model.toCategoryUi
import com.gpluslf.remindme.ui.theme.RemindMeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddListScreen(
    modifier: Modifier = Modifier,
    isNew: Boolean = false,
    state: TodoListState = TodoListState(),
    onAddListAction: (AddListAction) -> Unit = {},
    onFloatingActionButtonClick: () -> Unit = {},
    onCloseActionButtonClick: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddListAction(AddListAction.SaveList)
                    onFloatingActionButtonClick()
                }
            ) {
                Icon(Icons.Outlined.Save, "Save List")
            }
        },
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(horizontal = 30.dp),
                expandedHeight = 80.dp,
                title = {
                    if (isNew) {
                        Text(
                            stringResource(R.string.new_list),
                            style = MaterialTheme.typography.headlineLarge
                        )
                    } else {
                        Text(
                            stringResource(R.string.edit_list),
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onCloseActionButtonClick, content = {
                        Icon(
                            Icons.Outlined.Close, contentDescription = "Close",
                        )
                    })
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier
                .padding(contentPadding)
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp),
        ) {

            if (state.isPickerVisible) {
                ImagePickerBottomSheet(
                    onSelected = { image ->
                        onAddListAction(AddListAction.UpdateImage(image))
                    },
                    onDismissRequest = {
                        onAddListAction(AddListAction.ShowPicker(false))
                    }
                )
            }
            if (isNew) {
                CustomTextField(
                    stringResource(R.string.title),
                    state.title
                ) {
                    onAddListAction(AddListAction.UpdateTitle(it))
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        state.title,
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
            state.body?.let { text ->
                CustomTextField(
                    stringResource(R.string.body),
                    text
                ) {
                    onAddListAction(AddListAction.UpdateBody(it))
                }
            }
            if (state.categories.isNotEmpty()) {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Adaptive(minSize = 100.dp),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.categories) { category ->
                        FilterChip(
                            selected = category == state.selectedCategory,
                            onClick = { onAddListAction(AddListAction.UpdateCategory(category)) },
                            label = {
                                Text(
                                    category.title,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            },
                        )
                    }
                }
            }

            CustomPhotoButton { onAddListAction(AddListAction.ShowPicker(true)) }

            state.image?.let { image ->
                val painter = rememberAsyncImagePainter(
                    ImageRequest
                        .Builder(LocalContext.current)
                        .data(data = image)
                        .build()
                )
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_9_pro")
@Composable
private fun AddListScreenPreviewLight() {
    RemindMeTheme {
        Scaffold { padding ->
            AddListScreen(
                Modifier
                    .padding(padding)
                    .fillMaxSize(),
                state = sampleTodoListState
            )
        }
    }
}

@Preview(
    showBackground = true, showSystemUi = true, device = "id:pixel_9_pro",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun AddListScreenPreviewDark() {
    RemindMeTheme {
        Scaffold { padding ->
            AddListScreen(
                Modifier
                    .padding(padding)
                    .fillMaxSize(),
                state = sampleTodoListState
            )
        }
    }
}

internal val sampleTodoListState = TodoListState(
    title = "Sample List",
    categories = (1..10).map {
        Category(
            id = it.toLong(),
            title = "title $it",
            userId = 1
        ).toCategoryUi()
    }

)