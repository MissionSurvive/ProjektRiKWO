package com.example.meetingapp.Converters

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun fromLongToDate(value: Long?): Date? {
        return value?.let {Date(it)}
    }

    @TypeConverter
    fun fromDateToLong(date: Date?): Long? {
        return date?.time?.toLong()
    }
}