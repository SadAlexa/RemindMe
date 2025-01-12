package com.gpluslf.remindme.home.presentation.screens

import android.content.Intent
import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
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
import com.gpluslf.remindme.core.data.mappers.toDate
import com.gpluslf.remindme.core.presentation.model.TagUi
import com.gpluslf.remindme.home.presentation.components.CustomPhotoButton
import com.gpluslf.remindme.home.presentation.components.CustomTextField
import com.gpluslf.remindme.home.presentation.model.AddTaskAction
import com.gpluslf.remindme.home.presentation.model.CreateTaskState
import com.gpluslf.remindme.ui.theme.RemindMeTheme
import java.text.DateFormat.getDateInstance
import java.text.DateFormat.getTimeInstance
import java.util.Calendar
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    modifier: Modifier = Modifier,
    state: CreateTaskState,
    onAddTaskAction: (AddTaskAction) -> Unit,
    onFloatingActionButtonClick: () -> Unit,
    onCloseButtonClick: () -> Unit
) {
    val context = LocalContext.current

    if (state.isDatePickerOpen) {
        DatePickerModal(
            onDateSelected = {
                onAddTaskAction(AddTaskAction.UpdateEndDate(it))
            },
            onDismiss = {
                onAddTaskAction(AddTaskAction.ShowDatePicker(false))
            }
        )
    }

    if (state.isTimePickerOpen) {
        TimePickerModal(
            onConfirm = {
                onAddTaskAction(AddTaskAction.UpdateEndTime(it))
            },
            onDismiss = {
                onAddTaskAction(AddTaskAction.ShowTimePicker(false))
            }
        )
    }
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddTaskAction(AddTaskAction.SaveTask)
                    onFloatingActionButtonClick()
                }
            ) {
                Icon(Icons.Outlined.Save, "Save Task")
            }
        },
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(horizontal = 30.dp),
                expandedHeight = 80.dp,
                title = {
                    Text(
                        stringResource(R.string.add_task),
                        style = MaterialTheme.typography.headlineLarge
                    )
                },
                actions = {
                    IconButton(onClick = onCloseButtonClick,
                        content = {
                            Icon(
                                Icons.Outlined.Close, contentDescription = "Close",
                            )
                        }
                    )
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier
                .padding(contentPadding)
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            CustomTextField(
                stringResource(R.string.task_title),
                state.title
            ) {
                onAddTaskAction(AddTaskAction.UpdateTitle(it))
            }

            CustomTextField(
                stringResource(R.string.list_body),
                state.body
            ) {
                onAddTaskAction(AddTaskAction.UpdateBody(it))
            }

            HorizontalDivider()

            AnimatedContent(
                state.endTime != null,
                label = "End Time",
            ) { endTimePresent ->
                if (endTimePresent) {
                    val date = getDateInstance()
                    val time = getTimeInstance()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(Icons.Default.DateRange, contentDescription = "Date")
                        Text(
                            "Ends: ${state.endTime?.let { date.format(it) }}",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.clickable {
                                onAddTaskAction(
                                    AddTaskAction.ShowDatePicker(
                                        true
                                    )
                                )
                            }
                        )
                        Text("${state.endTime?.let { time.format(it) }}",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.clickable {
                                onAddTaskAction(
                                    AddTaskAction.ShowTimePicker(
                                        true
                                    )
                                )
                            })
                        IconButton(onClick = {
                            onAddTaskAction(AddTaskAction.RemoveEndTime)
                        }, content = {
                            Icon(
                                Icons.Outlined.Close, contentDescription = "Close",
                            )
                        })
                    }
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onAddTaskAction(AddTaskAction.ShowDatePicker(true))
                            }
                    ) {
                        Icon(Icons.Default.DateRange, contentDescription = "Date")
                        Text(
                            "Add end time", style = MaterialTheme.typography.bodyLarge,

                            )
                    }
                }
            }
            HorizontalDivider()

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(minSize = 100.dp),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.tags) { tag ->
                    FilterChip(
                        selected = tag.isSelected,
                        onClick = { onAddTaskAction(AddTaskAction.UpdateTags(tag)) },
                        label = { Text(tag.title, style = MaterialTheme.typography.bodyLarge) },
                    )
                }
            }

            val launcher =
                rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { image ->
                    if (image != null) {
                        context.contentResolver.takePersistableUriPermission(
                            image,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )
                        onAddTaskAction(AddTaskAction.UpdateImage(image))
                    }
                }
            CustomPhotoButton(launcher)

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
                    modifier = Modifier
                        .size(150.dp, 150.dp)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(20.dp))
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Date?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis?.toDate())
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerModal(
    onConfirm: (Pair<Int, Int>) -> Unit,
    onDismiss: () -> Unit,
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    TimePickerDialog(
        onDismiss = { onDismiss() },
        onConfirm = {
            onConfirm(Pair(timePickerState.hour, timePickerState.minute))
            onDismiss()
        }
    ) {
        TimePicker(
            state = timePickerState,
        )
    }
}


@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Dismiss")
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text("OK")
            }
        },
        text = { content() }
    )
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_9_pro")
@Composable
private fun AddTaskScreenPreviewLight() {
    RemindMeTheme {
        Scaffold { padding ->
            AddTaskScreen(
                Modifier
                    .padding(padding)
                    .fillMaxSize(),
                state = sampleCreateTaskState,
                {},
                {},
                {}
            )
        }
    }
}

@Preview(
    showBackground = true, showSystemUi = true, device = "id:pixel_9_pro",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun AddTaskScreenPreviewDark() {
    RemindMeTheme {
        Scaffold { padding ->
            AddTaskScreen(
                Modifier
                    .padding(padding)
                    .fillMaxSize(),
                state = sampleCreateTaskState,
                {},
                {},
                {}
            )
        }
    }
}

internal val sampleCreateTaskState = CreateTaskState(
    title = "title",
    body = "body",
    endTime = Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000),
    tags = listOf(
        TagUi(
            id = 1,
            title = "title",
            listTitle = "listTitle",
            userId = 1
        )
    ),
    image = null
)