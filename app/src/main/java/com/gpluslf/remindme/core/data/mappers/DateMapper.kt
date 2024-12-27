package com.gpluslf.remindme.core.data.mappers
import java.util.Date

fun Long.toDate(): Date {
    return Date(this)
}

fun Date.toLong(): Long {
    return this.time
}