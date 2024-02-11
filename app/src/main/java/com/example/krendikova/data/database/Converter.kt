package com.example.krendikova.data.database

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun toList(value: String?) = value?.split(",") ?: emptyList()

    @TypeConverter
    fun fromList(value: List<String>?) = value?.joinToString(separator = ",") ?: ""
}