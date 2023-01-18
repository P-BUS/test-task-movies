package com.example.testtaskfore.data.model

import com.example.testtaskfore.utils.Constants
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UnsplashPhoto(
    val id: String = Constants.EMPTY_STRING,
    @Json(name = "created_at")
    val createdAt: String? = Constants.EMPTY_STRING,
    val description: String? = Constants.EMPTY_STRING,
    @Json(name = "alt_description")
    val altDescription: String? = Constants.EMPTY_STRING,
    @Json(name = "liked_by_user")
    val likedByUser: Boolean? = false,
    val likes: Int? = Constants.EMPTY_INT,
    val urls: Urls?,
    val user: User?,
)

@JsonClass(generateAdapter = true)
data class Urls(
    val full: String? = Constants.EMPTY_STRING,
    val regular: String? = Constants.EMPTY_STRING,
    val small: String? = Constants.EMPTY_STRING,
    @Json(name = "small_s3")
    val smallS3: String? = Constants.EMPTY_STRING,
    val thumb: String? = Constants.EMPTY_STRING
)

@JsonClass(generateAdapter = true)
data class User(
    val name: String? = Constants.EMPTY_STRING
)






