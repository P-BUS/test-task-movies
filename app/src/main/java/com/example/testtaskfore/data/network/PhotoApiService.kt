package com.example.testtaskfore.data.network

import retrofit2.Response
import retrofit2.http.GET

interface PhotoApiService {
    @GET
    suspend fun getAllPhotos(): Response<UnsplashPhotosResponse>

}