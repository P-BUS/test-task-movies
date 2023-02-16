package com.example.testtaskfore.data.repository

import android.util.Log
import com.example.testtaskfore.data.database.MovieLocalDataSource
import com.example.testtaskfore.data.model.MovieModel
import com.example.testtaskfore.data.network.ApiResult
import com.example.testtaskfore.data.network.MovieRemoteDataSource
import com.example.testtaskfore.utils.Mappers.asDomainModel
import com.example.testtaskfore.utils.Mappers.fromNetworkToDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

const val TAG = "MovieRepository"
@Singleton
class MoviesRepository @Inject constructor(
    private val network: MovieRemoteDataSource,
    private val database: MovieLocalDataSource
) {

    val movies: Flow<List<MovieModel>> =
        database.getAllMovies()
            .map { it.asDomainModel() }

    suspend fun refreshMovies() {
        withContext(Dispatchers.IO) {
            when (val response = network.getMovies()) {
                is ApiResult.Success -> {
                    // Retrieve movies from network
                    val moviesList = response.data.results
                    // Update database
                    database.deleteAll()
                    database.insertAll(moviesList.fromNetworkToDatabaseModel())
                }
                is ApiResult.Error -> Log.e(TAG, "${response.code} ${response.message}")
                is ApiResult.Exception -> Log.e(TAG, "${response.e.cause} ${response.e.message}")
            }
        }
    }
}