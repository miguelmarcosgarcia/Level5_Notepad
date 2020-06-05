package com.example.notepad_level5.db

import androidx.room.TypeConverter
import java.util.*

class Converters {

    @TypeConverter
    fun fromTimeStamp(value:Long?): Date?{
        return value?.let{Date(it)}
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }


}