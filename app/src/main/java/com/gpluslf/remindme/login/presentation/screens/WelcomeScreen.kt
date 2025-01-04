package com.gpluslf.remindme.login.presentation.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.gpluslf.remindme.login.presentation.model.LoginAction
import com.gpluslf.remindme.ui.theme.RemindMeTheme

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    onLoginAction: (LoginAction) -> Unit,
) {
    Column(
        modifier.padding(horizontal = 50.dp, vertical = 70.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(40.dp),
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
        CustomButton(
            stringResource(R.string.sign_in)
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
                stringResource(R.string.sign_up)
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

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_9_pro")
@Composable
private fun WelcomeScreenPreviewLight() {
    RemindMeTheme {
        Scaffold  { padding ->
            WelcomeScreen(
                Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {}
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_9_pro",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun WelcomeScreenPreviewDark() {
    RemindMeTheme {
        Scaffold  { padding ->
            WelcomeScreen(
                Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {}
        }
    }
}