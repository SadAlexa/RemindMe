package com.gpluslf.remindme.core.presentation

import android.graphics.BitmapFactory
import com.gpluslf.remindme.core.domain.Image
import com.gpluslf.remindme.core.domain.Task
import com.gpluslf.remindme.core.domain.TodoList

fun Image.toBitMap() = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)