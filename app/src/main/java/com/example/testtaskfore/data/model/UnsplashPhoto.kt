package com.example.testtaskfore.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.coroutines.flow.MutableSharedFlow

@JsonClass(generateAdapter = true)
data class UnsplashPhoto(
    val id: String,
    @Json(name = "created_at")
    val createdAt: String,
    val description: String? = null,
    @Json(name = "alt_description")
    val altDescription: String? = null,
    @Json(name = "liked_by_user")
    val likedByUser: Boolean? = null,
    val likes: Int,
    val urls: Urls,
    val user: User
)

@JsonClass(generateAdapter = true)
data class Urls(
    val full: String? = null,
    val regular: String? = null,
    val small: String,
    @Json(name = "small_s3")
    val smallS3: String? = null,
    val thumb: String? = null
)

@JsonClass(generateAdapter = true)
data class User(
    val name: String? = null
)






