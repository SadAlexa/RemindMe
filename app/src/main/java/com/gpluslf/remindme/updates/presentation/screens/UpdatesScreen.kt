package com.gpluslf.remindme.updates.presentation.screens

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gpluslf.remindme.core.domain.Notification
import com.gpluslf.remindme.core.presentation.components.NoItemsPlaceholder
import com.gpluslf.remindme.home.presentation.components.SwipeToDeleteContainer
import com.gpluslf.remindme.ui.theme.RemindMeTheme
import com.gpluslf.remindme.updates.presentation.NotificationsState
import com.gpluslf.remindme.updates.presentation.model.NotificationAction
import com.gpluslf.remindme.updates.presentation.model.toNotificationUi
import java.text.DateFormat.getTimeInstance
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatesScreen(
    state: NotificationsState,
    onNotificationAction: (NotificationAction) -> Unit,
    onTaskNotificationClick: (String) -> Unit,
    onAchievementNotificationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(horizontal = 30.dp),
                expandedHeight = 80.dp,
                title = {
                    Text(
                        "Updates",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
            )
        }
    ) { contentPadding ->
        if (state.notifications.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .padding(top = contentPadding.calculateTopPadding())
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                items(state.notifications.reversed(), key = { it.id }) { item ->
                    SwipeToDeleteContainer(
                        item,
                        onDelete = {
                            onNotificationAction(NotificationAction.Delete(item))
                        },
                        modifier = Modifier.animateItem()
                    ) {
                        ListItem(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(20.dp))
                                .clickable {
                                    onNotificationAction(NotificationAction.Click(item))
                                    if (item.taskListTitle != null) {
                                        onTaskNotificationClick(item.taskListTitle)
                                    } else {
                                        onAchievementNotificationClick()
                                    }
                                },
                            colors = ListItemDefaults.colors(
                                containerColor = if (item.isRead) {
                                    MaterialTheme.colorScheme.surfaceContainerLow
                                } else {
                                    MaterialTheme.colorScheme.primaryContainer
                                }
                            ),
                            headlineContent = {
                                Column {
                                    Text(
                                        item.title,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        item.body,
                                        fontWeight = FontWeight.Medium,
                                    )
                                    Text(
                                        getTimeInstance().format(item.sendTime),
                                    )
                                }
                            },
                            trailingContent = {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowRight,
                                    contentDescription = "Go to"
                                )
                            },
                        )
                    }
                }
            }
        } else {
            NoItemsPlaceholder(Modifier.padding(contentPadding), "You have no updates")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_9_pro")
@Composable
private fun UpdatesScreenPreviewLight() {
    RemindMeTheme {
        Scaffold { padding ->
            UpdatesScreen(
                state = NotificationsState(
                    notifications = (0..10).map {
                        sampleNotifications.toNotificationUi()
                            .copy(id = it.toLong(), title = "Notification $it")
                    }
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

internal val sampleNotifications = Notification(
    id = 0,
    title = "title",
    body = "body",
    userId = 1,
    sendTime = Date.from(Date().toInstant()),
    isRead = false,
)

@Preview(
    showBackground = true, showSystemUi = true, device = "id:pixel_9_pro",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun UpdatesScreenPreviewDark() {
    RemindMeTheme {
        Scaffold { padding ->
            UpdatesScreen(
                state = NotificationsState(
                    notifications = (0..10).map {
                        sampleNotifications.toNotificationUi()
                            .copy(id = it.toLong(), title = "Notification $it")
                    }
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