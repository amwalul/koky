package com.amwa.koky.data.local.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringTypeConverter {

    @TypeConverter
    fun fromStringList(value: List<String>): String = with(Gson()) {
        val type = object : TypeToken<List<String>>() {}.type
        toJson(value, type)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> = with(Gson()) {
        val type = object : TypeToken<List<String>>() {}.type
        fromJson(value, type)
    }
}