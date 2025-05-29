package com.gpluslf.remindme.core.data.database

import androidx.room.TypeConverter
import com.gpluslf.remindme.core.data.database.DefaultUUID.Companion.DEFAULT_UUID
import java.util.UUID


class Converters {

    @TypeConverter
    fun fromUUID(uuid: UUID): String? {
        if (uuid == DEFAULT_UUID)
            return UUID.randomUUID().toString()

        return uuid.toString()
    }

    @TypeConverter
    fun uuidFromString(string: String?): UUID {
        string?.let {
            return UUID.fromString(string)
        }
        return DEFAULT_UUID
    }

}