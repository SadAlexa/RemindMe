package com.gpluslf.remindme.profile.presentation.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
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
import coil.request.ImageRequest
import com.gpluslf.remindme.core.domain.Achievement
import com.gpluslf.remindme.core.domain.User
import com.gpluslf.remindme.home.presentation.ListsState
import com.gpluslf.remindme.home.presentation.components.sampleTodoList
import com.gpluslf.remindme.home.presentation.model.toTodoListUi
import com.gpluslf.remindme.profile.presentation.AchievementState
import com.gpluslf.remindme.profile.presentation.UserState
import com.gpluslf.remindme.profile.presentation.model.toAchievementUi
import com.gpluslf.remindme.profile.presentation.model.toUserUi
import com.gpluslf.remindme.ui.theme.RemindMeTheme

@Composable
fun ProfileScreen(
    userState: UserState,
    achievementState: AchievementState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
    ) { contentPadding ->
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(contentPadding)
            .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val painter = rememberAsyncImagePainter(
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(data = if (userState.user?.image != null) userState.user.image else "https://cdn-icons-png.flaticon.com/512/149/149071.png")
                    .build()
            )
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .size(250.dp, 250.dp)
                    .padding(16.dp)
                    .clip(CircleShape)
            )
            Text(
                text = userState.user?.name ?: "",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            )
            Text(
                text = userState.user?.username ?: "",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(
                modifier = Modifier.size(16.dp)
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                for (achievement in achievementState.achievements) {
                    item {
                        ListItem(
                            headlineContent = { Text(achievement.title) },
                            supportingContent = {
                                Text(achievement.body)
                            },
                            colors = ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                            ),
                            trailingContent = {
                                Box(
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(70.dp),
                                        progress = { achievement.percentage / 100f },
                                        strokeWidth = 7.dp,
                                    )
                                    Text(
                                        "${achievement.percentage}%",
                                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.primary
                                        )
                                }

                            },
                            modifier = Modifier.clip(RoundedCornerShape(20.dp))
                        )
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_9_pro")
@Composable
private fun ProfileScreenPreviewLight() {
    RemindMeTheme {
        Scaffold  { padding ->
            ProfileScreen(
                userState = UserState(
                    User(
                        id = 1,
                        name = "name",
                        username = "username",
                        image = null,
                        password = "password",
                        salt = "salt",
                        email = "email"
                    ).toUserUi()
                ),
                achievementState = AchievementState(
                    achievements = (0..10).map { sampleAchievement.toAchievementUi().copy(title = "achievement $it") }
                ),
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize())
        }
    }
}

internal val sampleAchievement = Achievement(
    title = "title",
    userId = 1,
    body = "body",
    isCompleted = false,
    percentage = 80
)

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_9_pro",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun ProfileScreenPreviewDark() {
    RemindMeTheme {
        Scaffold  { padding ->
            ProfileScreen(
                userState = UserState(
                    User(
                        id = 1,
                        name = "name",
                        username = "username",
                        image = null,
                        password = "password",
                        salt = "salt",
                        email = "email"
                    ).toUserUi()
                ),
                achievementState = AchievementState(
                    achievements = (0..10).map { sampleAchievement.toAchievementUi().copy(title = "achievement $it") }
                ),
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize())
        }
    }
}
