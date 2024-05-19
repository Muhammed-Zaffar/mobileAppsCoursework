package com.example.myapplication.data;

import android.net.Uri
import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromUriListToString(uriList: List<Uri>?): String? {
        return uriList?.joinToString(",") { it.toString() }
    }

    @TypeConverter
    fun fromStringToUriList(data: String?): List<Uri>? {
        return data?.split(",")?.map { Uri.parse(it) }
    }
}

