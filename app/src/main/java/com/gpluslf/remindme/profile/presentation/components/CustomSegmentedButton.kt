package com.gpluslf.remindme.profile.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gpluslf.remindme.profile.domain.Theme
import com.gpluslf.remindme.profile.presentation.model.toThemeUi

@Composable
fun CustomSegmentedButton(
    selectedTheme: Theme,
    onThemeChange: (Theme) -> Unit
) {
    MultiChoiceSegmentedButtonRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        Theme.entries.forEachIndexed { index, theme ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = Theme.entries.size
                ),
                checked = selectedTheme == theme,
                onCheckedChange = {
                    onThemeChange(theme)
                },
                label = {
                    val themeUi = theme.toThemeUi()
                    Icon(
                        imageVector = themeUi.icon,
                        contentDescription = null
                    )
                }
            )
        }
    }
}