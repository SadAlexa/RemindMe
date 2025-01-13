package com.gpluslf.remindme.login.presentation.screens

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gpluslf.remindme.R
import com.gpluslf.remindme.login.presentation.components.CustomButton
import com.gpluslf.remindme.login.presentation.components.CustomOutlinedTextField
import com.gpluslf.remindme.login.presentation.model.LoginAction
import com.gpluslf.remindme.login.presentation.model.LoginEvent
import com.gpluslf.remindme.login.presentation.model.SignInAction
import com.gpluslf.remindme.login.presentation.model.SignInState
import com.gpluslf.remindme.ui.theme.RemindMeTheme
import kotlinx.coroutines.channels.Channel

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    state: SignInState = SignInState(),
    events: Channel<LoginEvent> = Channel(),
    onSignInAction: (SignInAction) -> Unit = {},
    onLoginAction: (LoginAction) -> Unit = {},
) {

    val context = LocalContext.current

    LaunchedEffect(events) {
        for (event in events) {
            when (event) {
                LoginEvent.LoginFailed -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.login_error_message),
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        }
    }

    Column(
        modifier.padding(horizontal = 50.dp, vertical = 70.dp),
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
            stringResource(R.string.sign_in),
            fontSize = 60.sp,
            fontWeight = FontWeight.Black
        )
        CustomOutlinedTextField(
            stringResource(R.string.email),
            state.email,
            onValueChange = {
                onSignInAction(SignInAction.UpdateEmail(it))
            },
            isError = state.loginError
        )
        CustomOutlinedTextField(
            stringResource(R.string.password),
            state.password,
            onValueChange = {
                onSignInAction(SignInAction.UpdatePassword(it))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            isPassword = true,
            isError = state.loginError
        )
        CustomButton(
            stringResource(R.string.sign_in)
        ) {
            onLoginAction(LoginAction.SignIn)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_9_pro")
@Composable
private fun WelcomeScreenPreviewLight() {
    RemindMeTheme {
        Scaffold { padding ->
            SignInScreen(
                Modifier
                    .padding(padding)
                    .fillMaxSize()
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
            SignInScreen(
                Modifier
                    .padding(padding)
                    .fillMaxSize()
            )
        }
    }
}