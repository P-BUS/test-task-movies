package com.example.testtaskfore.utils

import androidx.room.TypeConverter
import com.example.testtaskfore.data.model.Urls
import com.google.gson.Gson

class ConverterUrls {
    @TypeConverter
    fun urlsToJson(value: Urls?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToUrls(value: String) = Gson().fromJson(value, Urls::class.java)
}