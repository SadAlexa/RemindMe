package com.gpluslf.remindme.login.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun CustomButton(
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    FilledTonalButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        enabled = enabled
    ) {
        Text(
            text,
            fontSize = 25.sp,
            lineHeight = 40.sp
        )
    }
}