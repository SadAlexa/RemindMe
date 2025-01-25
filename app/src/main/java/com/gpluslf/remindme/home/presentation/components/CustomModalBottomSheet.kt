package com.gpluslf.remindme.home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gpluslf.remindme.ui.theme.RemindMeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : Any> CustomModalBottomSheet(
    action: (T?) -> Unit,
    elements: List<T>,
    key: (T) -> String = { it.toString() },
    onDismissRequest: () -> Unit,
    selected: T? = null,
    deleteAction: (T) -> Unit,
    editAction: (T) -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState()

    val onClick = { componentUi: T? ->
        action(componentUi)
        onDismissRequest()
    }

    ModalBottomSheet(
        sheetState = bottomSheetState,
        onDismissRequest = onDismissRequest
    ) {
        LazyColumn(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                CustomModalBottomSheetTextItem(
                    title = "All",
                    onClick = { onClick(null) },
                    isSelected = selected == null
                )
            }
            items(elements, key) { comp: T ->
                SwipeToDeleteContainer(
                    item = comp,
                    onDelete = { deleteAction(comp) },
                    onEdit = { editAction(comp) }
                ) {
                    CustomModalBottomSheetTextItem(
                        title = key(comp),
                        onClick = { onClick(comp) },
                        isSelected = selected == comp
                    )
                }
            }
        }
    }
}

@Composable
fun CustomModalBottomSheetTextItem(title: String, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() },
        colors =
        if (isSelected) CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ) else {
            CardDefaults.cardColors()
        }
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview
@Composable
private fun CustomModalBottomSheetPreview() {
    RemindMeTheme {
        Surface {
            CustomModalBottomSheet(
                action = {},
                elements = listOf("1", "2", "3"),
                onDismissRequest = {},
                deleteAction = {},
                editAction = {}
            )
        }
    }
}