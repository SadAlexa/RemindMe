package com.gpluslf.remindme.home.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gpluslf.remindme.ui.theme.RemindMeTheme

@Composable
fun CustomBasicTextField(text: String, value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp)),
        label = { Text(text = text) },
        placeholder = { Text(text = text) }
    )
}

@Preview
@Composable
private fun CustomBasicTextFieldPreview() {
    RemindMeTheme {
        Surface {
            CustomBasicTextField(
                text = "Title",
                value = "Title",
                onValueChange = {}
            )
        }
    }
}
