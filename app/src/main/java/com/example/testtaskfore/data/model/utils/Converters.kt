package com.example.testtaskfore.data.model.utils

import androidx.room.TypeConverter
import com.example.testtaskfore.data.model.Urls
import com.example.testtaskfore.data.model.User
import com.google.gson.Gson

class ConverterUrls {
    @TypeConverter
    fun urlsToJson(value: Urls?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToUrls(value: String) = Gson().fromJson(value, Urls::class.java)
}

class ConverterUser {
    @TypeConverter
    fun userToJson(value: User?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToUser(value: String) = Gson().fromJson(value, User::class.java)
}