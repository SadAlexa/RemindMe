package com.gpluslf.remindme.core.presentation.components

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.gpluslf.remindme.core.domain.Tag
import com.gpluslf.remindme.core.domain.Task
import com.gpluslf.remindme.core.presentation.model.TagUi
import com.gpluslf.remindme.core.presentation.model.TaskUi
import com.gpluslf.remindme.core.presentation.model.toTaskUi
import com.gpluslf.remindme.ui.theme.RemindMeTheme
import java.text.DateFormat.getDateTimeInstance
import java.util.Date

@Composable
fun TaskItem(
    task: TaskUi,
    onTagClick: (TagUi) -> Unit,
    onTaskClick: () -> Unit = {},
    onCheckClick: (() -> Unit)? = null
) {

    val context = LocalContext.current
    ListItem(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable {
                if (onCheckClick != null) {
                    onCheckClick()
                } else {
                    onTaskClick()
                }
            },
        headlineContent = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = task.title,
                        fontWeight = FontWeight.Bold,
                    )
                    task.endTime?.let {
                        Text(
                            text = getDateTimeInstance().format(task.endTime),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                if (task.coordinates != null) {
                    IconButton(
                        modifier = Modifier.size(30.dp),
                        onClick = {
                            val uri =
                                Uri.parse("geo:${task.coordinates.latitude},${task.coordinates.longitude}?z=16")

                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            context.startActivity(intent)
                        }
                    ) {
                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = "Location",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(30.dp),
                        )
                    }
                }
            }
        },
        supportingContent = {
            Column {
                task.body?.let { Text(it) }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    task.tags.forEach { item ->
                        FilterChip(
                            selected = false,
                            label = { Text(item.title) },
                            onClick = {
                                onTagClick(item)
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
                    painter = rememberAsyncImagePainter(task.image),
                    contentDescription = "Image",
                    modifier = Modifier
                        .size(70.dp)
                        .padding(8.dp)
                        .clip(CircleShape)
                )
            }
        },
        trailingContent = {
            AnimatedContent(onCheckClick != null) {
                if (it) {
                    Checkbox(
                        checked = task.isDone,
                        onCheckedChange = {
                            if (onCheckClick != null) {
                                onCheckClick()
                            }
                        }
                    )
                } else {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                        contentDescription = "Navigate"
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun CalendarItemPreview() {
    RemindMeTheme {
        Surface {
            TaskItem(
                task = sampleTask,
                onTagClick = {},
                onTaskClick = {}
            )
        }
    }
}

internal val sampleTask = Task(
    title = "Title",
    listTitle = "title",
    userId = 1,
    image = Uri.parse("https://cdn-icons-png.flaticon.com/512/149/149071.png"),
    endTime = Date.from(Date().toInstant()),
    body = "Lorem ipsum dolor sit amet",
    longitude = 14721986725629376.0,
    latitude = 14721986725629376.0,
    tags = listOf(
        Tag(
            id = 1,
            title = "Tag",
            listTitle = "title",
            userId = 1
        ),
        Tag(
            id = 2,
            title = "Tag2",
            listTitle = "title",
            userId = 1
        )
    )
).toTaskUi()