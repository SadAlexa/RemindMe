package com.gpluslf.remindme.core.data.database.entities

enum class Frequency(val value: String) {
    ONCE("once"),
    DAILY("daily"),
    WEEKLY("weekly"),
    MONTHLY("monthly"),
    YEARLY("yearly");

    companion object {
        fun fromValue(value: String): Frequency {
            return entries.first { it.value == value }
        }
    }
}