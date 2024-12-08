package com.gpluslf.remindme.core.data.database.utils

import androidx.room.TypeConverter
import com.gpluslf.remindme.core.data.database.entities.Alert
import com.gpluslf.remindme.core.data.database.entities.Frequency

class Converters {
    @TypeConverter
    fun toFrequency(value: String): Frequency {
        return Frequency.fromValue(value)
    }

    @TypeConverter
    fun fromFrequency(frequency: Frequency): String {
        return frequency.value
    }

    @TypeConverter
    fun toAlert(value: String): Alert {
        return Alert.fromValue(value)
    }

    @TypeConverter
    fun fromAlert(alert: Alert): String {
        return alert.value
    }
}
