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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
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
import com.gpluslf.remindme.core.domain.User
import com.gpluslf.remindme.core.domain.UserAchievement
import com.gpluslf.remindme.profile.presentation.UserAchievementState
import com.gpluslf.remindme.profile.presentation.UserState
import com.gpluslf.remindme.profile.presentation.model.ProfileAction
import com.gpluslf.remindme.profile.presentation.model.toUserAchievementUi
import com.gpluslf.remindme.profile.presentation.model.toUserUi
import com.gpluslf.remindme.ui.theme.RemindMeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userState: UserState,
    userAchievementState: UserAchievementState,
    onProfileAction: (ProfileAction) -> Unit,
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
                        "Your Profile",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    TextButton(
                        onClick = {
                            onProfileAction(ProfileAction.LogOut)
                        },
                    ) {
                        Text(
                            "Log Out",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
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
                    .size(150.dp)
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
                for (userAchievement in userAchievementState.achievements) {
                    item {
                        ListItem(
                            headlineContent = { Text(userAchievement.achievementTitle) },
                            supportingContent = {
                                Text(userAchievement.achievementBody)
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
                                        progress = { userAchievement.percentage.value },
                                        strokeWidth = 7.dp,
                                    )
                                    Text(
                                        text = userAchievement.percentage.formatted,
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
        Scaffold { padding ->
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
                userAchievementState = UserAchievementState(
                    achievements = (0..10).map {
                        sampleUserAchievement.toUserAchievementUi()
                            .copy(achievementTitle = "achievement $it")
                    }
                ),
                {},
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            )
        }
    }
}

internal val sampleUserAchievement = UserAchievement(
    1,
    "title",
    "AAAAAAA",
    1,
    1,
    1,
    true
)

@Preview(
    showBackground = true, showSystemUi = true, device = "id:pixel_9_pro",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun ProfileScreenPreviewDark() {
    RemindMeTheme {
        Scaffold { padding ->
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
                userAchievementState = UserAchievementState(
                    achievements = (0..10).map {
                        sampleUserAchievement.toUserAchievementUi()
                            .copy(achievementTitle = "achievement $it")
                    }
                ),
                {},
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            )
        }
    }
}
