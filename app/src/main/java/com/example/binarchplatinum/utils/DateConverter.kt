package com.example.binarchplatinum.utils

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

object DateConverters {
    @TypeConverter
    fun longToDate(date: Long): String {
        val dateToConvert = Date(date)
        return SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault()
        ).format(dateToConvert)
    }

    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }
}
