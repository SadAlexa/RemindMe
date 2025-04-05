package com.gpluslf.remindme.login.presentation.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gpluslf.remindme.R
import com.gpluslf.remindme.login.presentation.components.CustomButton
import com.gpluslf.remindme.login.presentation.components.CustomOutlinedTextField
import com.gpluslf.remindme.login.presentation.model.LoginAction
import com.gpluslf.remindme.login.presentation.model.LoginEvent
import com.gpluslf.remindme.login.presentation.model.SignUpAction
import com.gpluslf.remindme.login.presentation.model.SignUpState
import com.gpluslf.remindme.ui.theme.RemindMeTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    state: SignUpState = SignUpState(),
    events: Flow<LoginEvent> = emptyFlow(),
    onSignUpAction: (SignUpAction) -> Unit = {},
    onLoginAction: (LoginAction) -> Unit = {},
) {

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
            stringResource(R.string.sign_up),
            fontSize = 60.sp,
            fontWeight = FontWeight.Black
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(25.dp),
        ) {
            CustomOutlinedTextField(
                stringResource(R.string.username),
                state.username,
                onValueChange = {
                    onSignUpAction(SignUpAction.UpdateUsername(it))
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            CustomOutlinedTextField(
                stringResource(R.string.email),
                state.email,
                onValueChange = {
                    onSignUpAction(SignUpAction.UpdateEmail(it))
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            CustomOutlinedTextField(
                stringResource(R.string.password),
                state.password,
                onValueChange = {
                    onSignUpAction(SignUpAction.UpdatePassword(it))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                isPassword = true
            )
            CustomOutlinedTextField(
                stringResource(R.string.confirmPassword),
                state.confirmPassword,
                onValueChange = {
                    onSignUpAction(SignUpAction.UpdateConfirmPassword(it))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                isPassword = true
            )
            CustomButton(
                stringResource(R.string.sign_up)
            ) {
                onLoginAction(LoginAction.SignUp)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_9_pro")
@Composable
private fun WelcomeScreenPreviewLight() {
    RemindMeTheme {
        Scaffold { padding ->
            SignUpScreen(
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
            SignUpScreen(
                Modifier
                    .padding(padding)
                    .fillMaxSize()
            )
        }
    }
}