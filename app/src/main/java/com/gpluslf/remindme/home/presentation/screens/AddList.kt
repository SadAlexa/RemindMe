package com.gpluslf.remindme.home.presentation.screens

import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.gpluslf.remindme.R
import com.gpluslf.remindme.core.domain.Category
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
    state: TodoListState = TodoListState(),
    onAddListAction : (AddListAction) -> Unit = {},
    onFloatingActionButtonClick : () -> Unit = {},
    onCloseActionButtonClick: () -> Unit = {},
) {Scaffold(
    modifier = modifier,
    floatingActionButton = {
        FloatingActionButton(
            containerColor = MaterialTheme.colorScheme.primary,
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
                Text(
                    "Add List", style = MaterialTheme.typography.headlineLarge
                )
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
            CustomTextField (
                stringResource(R.string.list_title),
                state.title
            ) {
                onAddListAction(AddListAction.UpdateTitle(it))
            }

            CustomTextField(
                stringResource(R.string.list_body),
                state.body ?: ""
            ) {
                onAddListAction(AddListAction.UpdateBody(it))
            }

            LazyVerticalStaggeredGrid (
                columns = StaggeredGridCells.Adaptive(minSize = 100.dp),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ){
                items(state.categories) { category ->
                    FilterChip(
                        selected = category.isSelected,
                        onClick = { onAddListAction(AddListAction.UpdateCategory(category)) },
                        label = { Text(category.title, style = MaterialTheme.typography.bodyLarge) },
                    )
                }
            }

            val result = remember { mutableStateOf<Uri?>(null) }
            val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
                result.value = it
            }
            CustomPhotoButton(launcher)

            result.value?.let { image ->
                onAddListAction(AddListAction.UpdateImage(image))
                // Use Coil to display the selected image
                val painter = rememberAsyncImagePainter(
                    ImageRequest
                        .Builder(LocalContext.current)
                        .data(data = image)
                        .build()
                )
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp, 150.dp)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(20.dp))
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_9_pro")
@Composable
private fun AddListScreenPreviewLight() {
    RemindMeTheme {
        Scaffold  { padding ->
            AddListScreen(
                Modifier
                    .padding(padding)
                    .fillMaxSize(),
                state = sampleTodoListState
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_9_pro",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun AddListScreenPreviewDark() {
    RemindMeTheme {
        Scaffold  { padding ->
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