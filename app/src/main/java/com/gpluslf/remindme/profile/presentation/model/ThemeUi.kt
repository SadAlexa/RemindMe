package com.gpluslf.remindme.profile.presentation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.ui.graphics.vector.ImageVector
import com.gpluslf.remindme.profile.domain.Theme

data class ThemeUi(
    val title: String,
    val value: Theme,
    val icon: ImageVector
)

fun Int.toThemeUi(): ThemeUi {
    return when (this) {
        Theme.LIGHT.value -> ThemeUi("Light", Theme.LIGHT, Icons.Default.WbSunny)
        Theme.DARK.value -> ThemeUi("Dark", Theme.DARK, Icons.Default.Nightlight)
        else -> ThemeUi("System", Theme.SYSTEM, Icons.Default.AutoAwesome)
    }
}

fun Theme.toThemeUi(): ThemeUi {
    return when (this) {
        Theme.LIGHT -> ThemeUi("Light", Theme.LIGHT, Icons.Default.WbSunny)
        Theme.DARK -> ThemeUi("Dark", Theme.DARK, Icons.Default.Nightlight)
        Theme.SYSTEM -> ThemeUi("System", Theme.SYSTEM, Icons.Default.AutoAwesome)
    }
}