package com.gpluslf.remindme.home.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gpluslf.remindme.ui.theme.RemindMeTheme

data class FloatingActionButtonMenuItem(
    val icon: ImageVector,
    val text: String,
    val onSelected: (() -> Unit)? = null,
)

private const val ANIMATION_DURATION = 50

@Composable
fun FloatingActionAddButton(
    modifier: Modifier = Modifier,
    items: List<FloatingActionButtonMenuItem> = emptyList(),
) {
    var fabExpanded by remember { mutableStateOf(false) }
    val fabRotation by animateFloatAsState(if (fabExpanded) 45f else 0f, label = "fabRotation")
    val enterTransition =
        remember {
            fadeIn(
                initialAlpha = 0.3f,
                animationSpec = tween(ANIMATION_DURATION, easing = FastOutSlowInEasing),
            )
        }
    val exitTransition =
        remember {
            fadeOut(
                animationSpec = tween(ANIMATION_DURATION, easing = FastOutSlowInEasing),
            )
        }
    val numberOfItems by animateIntAsState(
        targetValue = if (fabExpanded) items.size else 0,
        animationSpec =
        tween(
            durationMillis = ANIMATION_DURATION * items.size,
            easing = LinearEasing,
        ),
        label = "numberOfItems",
    )
    val indices: List<Int> =
        if (numberOfItems == 0) {
            emptyList()
        } else {
            buildList {
                for (i in 0 until numberOfItems) {
                    add(items.size - i - 1)
                }
            }
        }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.End,
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            items.forEachIndexed { idx, item ->
                AnimatedVisibility(
                    visible = idx in indices,
                    enter = enterTransition,
                    exit = exitTransition,
                ) {
                    val onClickAction: () -> Unit = {
                        fabExpanded = false
                        item.onSelected?.invoke()
                    }
                    Row(
                        modifier =
                        Modifier.padding(end = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(
                            modifier =
                            Modifier
                                .padding(horizontal = 12.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { onClickAction() }
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    shape = RoundedCornerShape(20.dp),
                                )
                                .padding(
                                    vertical = 12.dp,
                                    horizontal = 16.dp,
                                ),
                            text = item.text,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                        Icon(
                            modifier =
                            Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .clickable { onClickAction() }
                                .background(
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    shape = CircleShape,
                                )
                                .padding(10.dp),
                            imageVector = item.icon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        FloatingActionButton(
            onClick = {
                fabExpanded = !fabExpanded
            },
            content = {
                Icon(
                    modifier = Modifier.rotate(fabRotation),
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            },
        )
    }
}

@Preview
@Composable
private fun FloatingActionAddButtonPreview() {
    RemindMeTheme {
        Surface {
            FloatingActionAddButton(

                items = listOf(
                    FloatingActionButtonMenuItem(
                        icon = Icons.Outlined.Category,
                        text = "Category",
                    ),
                    FloatingActionButtonMenuItem(
                        icon = Icons.AutoMirrored.Outlined.List,
                        text = "List",
                    ),
                ),
            )
        }
    }
}