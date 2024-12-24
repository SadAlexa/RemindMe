package com.gpluslf.remindme.core.presentation

import android.graphics.BitmapFactory
import com.gpluslf.remindme.core.domain.Image
import com.gpluslf.remindme.core.domain.Task
import com.gpluslf.remindme.core.domain.TodoList

fun TodoList.toBitMap(image: Image?) =
    if (image != null) {
        BitmapFactory.decodeByteArray(image.bytes, 0, image.bytes.size)
    } else {
        null
    }

fun Task.toBitMap(image: Image?) =
    if (image != null) {
        BitmapFactory.decodeByteArray(image.bytes, 0, image.bytes.size)
    } else {
        null
    }