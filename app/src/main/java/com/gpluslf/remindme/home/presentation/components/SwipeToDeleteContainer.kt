package com.gpluslf.remindme.home.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    onEdit: (T) -> Unit,
    animationDuration: Int = 500,
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit
) {
    var isRemoved by remember {
        mutableStateOf(false)
    }

    var isEdit by remember {
        mutableStateOf(false)
    }
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            when (value) {
                SwipeToDismissBoxValue.EndToStart -> {
                    isRemoved = true
                    true
                }

                SwipeToDismissBoxValue.StartToEnd -> {
                    isEdit = true
                    true
                }

                else -> false
            }
        }
    )

    LaunchedEffect(key1 = isRemoved, key2 = isEdit) {
        if (isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
        }

        if (isEdit) {
            onEdit(item)
            state.dismiss(SwipeToDismissBoxValue.Settled)
        }
    }

    AnimatedVisibility(
        modifier = modifier,
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismissBox(
            state = state,
            backgroundContent = { DismissBackground(state) },
            content = { content(item) }
        )
    }
}

@Composable
fun DismissBackground(
    swipeDismissState: SwipeToDismissBoxState
) {
    val color = when (swipeDismissState.dismissDirection) {
        SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.error
        SwipeToDismissBoxValue.StartToEnd -> MaterialTheme.colorScheme.primary
        else -> Color.Transparent
    }

    val icon = when (swipeDismissState.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> Icons.Default.Edit
        else -> Icons.Default.Delete
    }

    val alignment = when (swipeDismissState.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> Alignment.Start
        else -> Alignment.End
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(20.dp))
            .background(color)
            .padding(16.dp),
        horizontalAlignment = alignment,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.background
        )
    }
}
