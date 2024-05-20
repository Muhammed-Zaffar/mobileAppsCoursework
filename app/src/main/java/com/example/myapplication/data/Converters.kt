package com.example.myapplication.data;

import android.net.Uri
import androidx.room.TypeConverter
import java.sql.Date

class Converters {
    @TypeConverter
    fun fromUriListToString(uriList: List<Uri>?): String? {
        return uriList?.joinToString(",") { it.toString() }
    }

    @TypeConverter
    fun fromStringToUriList(data: String?): List<Uri>? {
        return data?.split(",")?.map { Uri.parse(it) }
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}

