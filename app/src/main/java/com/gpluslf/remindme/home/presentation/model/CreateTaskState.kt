package com.gpluslf.remindme.home.presentation.model

import android.net.Uri
import androidx.compose.runtime.Immutable
import com.gpluslf.remindme.core.domain.Coordinates
import com.gpluslf.remindme.core.presentation.model.TagUi
import java.util.Date

@Immutable
data class CreateTaskState(
    val title: String = "",

    val listTitle: String = "",

    val body: String? = null,

    val endTime: Date? = null,

    val frequency: Date? = null,

    val alert: Date? = null,

    val image: Uri? = null,

    val isDone: Boolean = false,

    val coordinates: Coordinates? = null,

    val selectedTags: List<TagUi> = emptyList(),

    val tags: List<TagUi> = emptyList(),

    val isTimePickerOpen: Boolean = false,

    val isDatePickerOpen: Boolean = false,

    val isMapOpen: Boolean = false,

    val isImagePickerVisible: Boolean = false
)
