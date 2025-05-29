package com.gpluslf.remindme.core.data.mappers

import com.gpluslf.remindme.core.data.database.DefaultUUID.Companion.DEFAULT_UUID
import java.util.UUID

fun String.toUUID(): UUID {
    try {
        return UUID.fromString(this)
    } catch (_: IllegalArgumentException) {
        return DEFAULT_UUID
    }
}