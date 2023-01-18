package com.example.testtaskfore.data.model

import com.squareup.moshi.Json

data class UnsplashPhoto(

    @Json(name = "created_at")
    val createdAt: String = "",
    val description: String = "",
    @Json(name = "alt_description")
    val altDescription: String = "",
    val id: String = "",
    @Json(name = "liked_by_user")
    val likedByUser: Boolean = false,
    val likes: Int = 0,
    val urls: Urls,
    val user: User,
)

data class Urls(
    val full: String = "",
    val regular: String = "",
    val small: String = "",
    @Json(name = "small_s3")
    val smallS3: String = "",
    val thumb: String = ""
)

data class User(
    val name: String
)






