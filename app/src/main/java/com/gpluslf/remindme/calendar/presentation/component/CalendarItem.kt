package com.gpluslf.remindme.calendar.presentation.component

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.gpluslf.remindme.core.domain.Tag
import com.gpluslf.remindme.core.domain.Task
import com.gpluslf.remindme.core.presentation.model.TaskUi
import com.gpluslf.remindme.core.presentation.model.toTaskUi
import com.gpluslf.remindme.ui.theme.RemindMeTheme
import java.text.DateFormat.getTimeInstance
import java.util.Date

@Composable
fun CalendarItem(task: TaskUi) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))

    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (task.image != null) {
                Image(
                    painter = rememberAsyncImagePainter(task.image),
                    contentDescription = "Image",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(8.dp)
                        .clip(CircleShape)
                )
            }
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = task.title,
                    fontWeight = FontWeight.Bold
                )
                task.endTime?.let {
                    Text(
                        text = getTimeInstance().format(task.endTime),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                //TODO add position
                task.body?.let {
                    Text(
                        text = it,
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    task.tags.forEach { item ->
                        FilterChip(
                            selected = false,
                            label = { Text(item.title) },
                            onClick = {}
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CalendarItemPreview() {
    RemindMeTheme {
        Surface {
            CalendarItem(
                task = sampleTask
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