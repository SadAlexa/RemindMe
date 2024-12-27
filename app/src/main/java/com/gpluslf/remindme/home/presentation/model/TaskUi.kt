package com.gpluslf.remindme.home.presentation.model

import android.graphics.Bitmap
import com.gpluslf.remindme.core.data.database.entities.TaskEntity
import com.gpluslf.remindme.core.domain.Image
import com.gpluslf.remindme.core.domain.Task
import com.gpluslf.remindme.core.presentation.toBitMap
import java.util.Date

data class TaskUi(
    val title: String,
    val listTitle: String,
    val userId: Long,
    val body: String?,
    val endTime: Date?,
    val frequency: Date?,
    val alert: Date?,
    val image: Bitmap?
)

fun Task.toTaskUi() = TaskUi(
    title,
    listTitle,
    userId,
    body,
    endTime,
    frequency,
    alert,
    image?.toBitMap(),
)

