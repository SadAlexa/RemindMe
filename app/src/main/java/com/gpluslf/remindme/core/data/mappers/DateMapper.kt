package com.gpluslf.remindme.core.data.mappers

import java.time.LocalDate
import java.time.ZoneOffset
import java.util.Date

fun Long.toDate(): Date {
    return Date(this)
}

fun Date.toLong(): Long {
    return this.time
}

fun LocalDate.toLong(): Long {
    return this.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
}
