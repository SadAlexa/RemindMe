package com.gpluslf.remindme.home.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gpluslf.remindme.R
import com.gpluslf.remindme.ui.theme.RemindMeTheme

@Composable
fun CustomAlertDialog(
    dialogTitle: String,
    value: String,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    onTitleValueChange: (String) -> Unit
) {
    AlertDialog(
        title = {
            Text(text = dialogTitle)
        },
        text = {
            CustomTextField(
                stringResource(R.string.title),
                value
            ) {
                onTitleValueChange(it)
            }
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

@Preview
@Composable
private fun CustomAlertDialogPreview() {
    RemindMeTheme {
        Surface {
            CustomAlertDialog(
                onDismissRequest = {},
                onConfirmation = {},
                onTitleValueChange = {},
                value = "",
                dialogTitle = "Title"
            )
        }
    }
}