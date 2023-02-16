package com.example.testtaskfore.data.network

import com.example.testtaskfore.data.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET(ENDPOINT)
    suspend fun getMovies(
        @Query("api_key") clientId: String = ACCESS_KEY,
    ): Response<MovieResponse>

    companion object {
        const val ENDPOINT = "3/trending/movie/week"
        const val ACCESS_KEY = "d866f943f2d70dee6fcd311d094d5720"
    }

}