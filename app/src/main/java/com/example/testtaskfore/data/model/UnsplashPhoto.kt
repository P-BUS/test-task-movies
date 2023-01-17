package com.example.testtaskfore.data.model

import retrofit2.http.Url

data class UnsplashPhoto(
    val name: String,
    val url: Url,
    val likes: Int
)