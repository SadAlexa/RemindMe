package com.gpluslf.remindme.home.presentation.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.ListItem
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
import com.gpluslf.remindme.core.domain.Tag
import com.gpluslf.remindme.core.domain.Task
import com.gpluslf.remindme.core.presentation.model.TaskUi
import com.gpluslf.remindme.core.presentation.model.toTaskUi
import com.gpluslf.remindme.ui.theme.RemindMeTheme

@Composable
fun CustomTaskItem(task: TaskUi, onTagClick: () -> Unit, onTaskClick: () -> Unit = {}) {
    ListItem(
        modifier = Modifier.clip(RoundedCornerShape(20.dp))
            .clickable { onTaskClick() },
        headlineContent = { Text(task.title) },
        supportingContent = {
            Column {
                task.body?.let { Text(it) }
                Row (
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ){
                    task.tags.forEach { item ->
                        FilterChip(
                            selected = false,
                            label = { Text(item.title) },
                            onClick = {
                                onTagClick()
                            }
                        )
                    }
                }
            }
        },
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ),
        leadingContent = {
            task.image?.let {
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
            Checkbox(
                checked = task.isDone,
                onCheckedChange = {/*TODO*/}
            )
        }
    )
}

@Preview
@Composable
private fun CustomTaskItemPreview() {
    RemindMeTheme {
        Surface {
            CustomTaskItem(
                task = sampleTask.toTaskUi(),
                onTagClick = {},
                onTaskClick = {}
            )
        }
    }
}

internal val sampleTask = Task(
    title = "title",
    listTitle = "title",
    userId = 1,
    body = "body",
    image = null,
    tags = listOf(
        Tag(
            id = 1,
            title = "tag 1",
            listTitle = "title",
            userId = 1
        ),
        Tag(
            id = 2,
            title = "tag 2",
            listTitle = "title",
            userId = 1
        )
    )
)

