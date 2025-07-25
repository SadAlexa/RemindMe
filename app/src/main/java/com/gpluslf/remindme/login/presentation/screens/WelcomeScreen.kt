package com.gpluslf.remindme.login.presentation.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gpluslf.remindme.R
import com.gpluslf.remindme.login.presentation.components.CustomButton
import com.gpluslf.remindme.login.presentation.components.CustomOutlinedTextField
import com.gpluslf.remindme.login.presentation.model.LoginAction
import com.gpluslf.remindme.login.presentation.model.WelcomeAction
import com.gpluslf.remindme.login.presentation.model.WelcomeState
import com.gpluslf.remindme.ui.theme.RemindMeTheme

@Composable
fun WelcomeScreen(
    state: WelcomeState,
    modifier: Modifier = Modifier,
    onLoginAction: (LoginAction) -> Unit,
    onWelcomeAction: (WelcomeAction) -> Unit
) {
    Column(
        modifier
            .padding(end = 50.dp, start = 50.dp, top = 70.dp, bottom = 0.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp),
    ) {
        Image(
            painterResource(R.drawable.remindmeicon),
            null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.size(120.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
        )

        Text(
            "${stringResource(R.string.hello)}!",
            fontSize = 60.sp,
            fontWeight = FontWeight.Black
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CustomOutlinedTextField(
                stringResource(R.string.server_url),
                state.serverUrl,
                onValueChange = { onWelcomeAction(WelcomeAction.EditServerUrl(it)) },
                modifier = modifier.weight(0.8f),
                isError = state.isError
            )
            Button(
                onClick = { onWelcomeAction(WelcomeAction.ValidateServerUrl) },
                modifier = Modifier
                    .size(50.dp),
                contentPadding = PaddingValues(0.dp),
                enabled = !state.loading
            ) {
                if (state.loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(10.dp),
                    )
                } else {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward,
                        null,
                    )
                }
            }
        }

        CustomButton(
            stringResource(R.string.sign_in),
            enabled = state.isServerUrlValid
        ) {
            onLoginAction(LoginAction.SignIn)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                stringResource(
                    R.string.not_registered
                ),
                fontSize = 20.sp,
                lineHeight = 40.sp,
                fontWeight = FontWeight.Bold,
            )
            CustomButton(
                stringResource(R.string.sign_up),
                enabled = state.isServerUrlValid
            ) {
                onLoginAction(LoginAction.SignUp)
            }
        }
        Spacer(Modifier.weight(1f))
        CustomButton(
            stringResource(R.string.guest)
        ) {
            onLoginAction(LoginAction.Guest)
        }
    }
}

internal val sampleWelcomeState = WelcomeState(
    serverUrl = "https://example.com",
    isServerUrlValid = true
)

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_9_pro")
@Composable
private fun WelcomeScreenPreviewLight() {
    RemindMeTheme {
        Scaffold { padding ->
            WelcomeScreen(
                state = sampleWelcomeState,
                modifier =
                    Modifier
                        .padding(padding)
                        .fillMaxSize(),
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
private fun WelcomeScreenPreviewDark() {
    RemindMeTheme {
        Scaffold { padding ->
            WelcomeScreen(
                state = sampleWelcomeState,
                modifier =
                    Modifier
                        .padding(padding)
                        .fillMaxSize(),
                {},
                {}
            )
        }
    }
}