package com.example.testtaskfore.data.network

import com.example.testtaskfore.data.model.MovieResponse
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieApiService: MovieApiService
) {
    suspend fun getMovies(): ApiResult<MovieResponse> =
        handleApiResponse {
            movieApiService.getMovies()
        }
}