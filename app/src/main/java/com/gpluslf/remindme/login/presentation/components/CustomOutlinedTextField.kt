package com.gpluslf.remindme.login.presentation.components

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun CustomOutlinedTextField(text: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
            )
        },
        placeholder = {
            Text(
                text,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
            )
        },
        singleLine = true
    )
}