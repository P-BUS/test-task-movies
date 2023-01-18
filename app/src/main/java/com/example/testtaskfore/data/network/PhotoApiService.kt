package com.example.testtaskfore.data.network

import com.example.testtaskfore.data.model.UnsplashPhotosResponse
import com.example.testtaskfore.utils.Constants.ACCESS_KEY
import com.example.testtaskfore.utils.Constants.ENDPOINT
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PhotoApiService {
    @GET(ENDPOINT)
    suspend fun getPhotos(
        @Query("client_id") clientId: String = ACCESS_KEY,
        @Query("page") pageNumber: Int = 1,
        @Query("per_page") itemsPerPage: Int = 10,
        @Query("order_by") sort: String = "popular"
    ): UnsplashPhotosResponse

}