package com.example.testtaskfore.data.model

import com.squareup.moshi.Json

data class UnsplashPhoto(
    val id: String,
    val name: String,
    @Json(name = "img_src") val imgSrcUrl: String,
    val likes: Int
)