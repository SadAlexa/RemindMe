package com.gpluslf.remindme.home.presentation.model

import android.net.Uri
import androidx.compose.runtime.Immutable
import com.gpluslf.remindme.core.presentation.model.TagUi
import java.util.Date

@Immutable
data class CreateTaskState (
    val title: String = "",

    val listTitle: String = "",

    val body: String = "",

    val endTime: Date? = null,

    val frequency: Date? = null,

    val alert: Date? = null,

    val image: Uri? = null,

    val isDone: Boolean = false,

    val latitude: Double? = null,

    val longitude: Double? = null,

    val tags : List<TagUi> = emptyList(),

    val isTimePickerOpen: Boolean = false,

    val isDatePickerOpen: Boolean = false,
)
