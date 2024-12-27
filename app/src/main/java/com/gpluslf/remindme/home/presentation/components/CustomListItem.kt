package com.gpluslf.remindme.home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.gpluslf.remindme.core.domain.Image
import com.gpluslf.remindme.core.domain.TodoList
import com.gpluslf.remindme.home.presentation.model.TodoListUi
import com.gpluslf.remindme.home.presentation.model.toTodoListUi
import com.gpluslf.remindme.ui.theme.RemindMeTheme

@Composable
fun CustomListItem(item: TodoListUi, onClick: () -> Unit = {}) {
        ListItem(
            modifier = Modifier.clip(RoundedCornerShape(20.dp))
                .clickable { onClick() },
            headlineContent = { Text(item.title) },
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
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(8.dp))
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

internal val sampleTodoList = TodoList("title", 1, categoryId = 1, body = "body", image = Image(
    bytes = ByteArray(
    1000
    ).apply {
        repeat(1000) {
            this[it] = 1
        }
    },
))

