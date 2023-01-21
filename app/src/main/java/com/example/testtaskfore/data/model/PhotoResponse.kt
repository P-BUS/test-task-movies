package com.example.testtaskfore.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotoResponse(
    val results: List<UnsplashPhoto>,
    val total: Int,
    val total_pages: Int
)





