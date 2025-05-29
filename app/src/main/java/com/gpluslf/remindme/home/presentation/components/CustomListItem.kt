package com.gpluslf.remindme.home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.gpluslf.remindme.core.domain.Category
import com.gpluslf.remindme.core.domain.TodoList
import com.gpluslf.remindme.core.presentation.components.ImageDialog
import com.gpluslf.remindme.core.presentation.model.TodoListUi
import com.gpluslf.remindme.core.presentation.model.toTodoListUi
import com.gpluslf.remindme.ui.theme.RemindMeTheme

@Composable
fun CustomListItem(item: TodoListUi, onClick: () -> Unit = {}) {

    var isDialogOpen by remember { mutableStateOf(false) }

    if (isDialogOpen) {
        item.image?.let { ImageDialog(imageUri = it, onDismiss = { isDialogOpen = false }) }
    }

    ListItem(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() },
        headlineContent = {
            Text(
                item.title,
                fontWeight = FontWeight.Bold,
            )
        },
        supportingContent = {
            item.body?.let { Text(it) }
        },
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ),
        leadingContent = {
            item.image?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .clickable { isDialogOpen = true }
                )
            }
        },
        trailingContent = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                contentDescription = "Navigate"
            )
        }
    )
}

@Preview
@Composable
private fun CustomListItemPreview() {
    RemindMeTheme {
        Surface {
            CustomListItem(
                item = sampleTodoList.toTodoListUi(),
                onClick = {}
            )
        }
    }
}

internal val sampleTodoList = TodoList(
    id = "",
    "title",
    1,
    body = "body",
    category = Category(
        id = "1",
        title = "title",
        userId = 1
    ),
)

