package com.gpluslf.remindme.core.data.database.entities

enum class Alert(val value: String) {
    DAY_BEFORE("day_before"),
    WEEK_BEFORE("week_before"),
    MONTH_BEFORE("month_before"),
    CUSTOM("custom");

    companion object {
        fun fromValue(value: String): Alert {
            return entries.first { it.value == value }
        }
    }
}


