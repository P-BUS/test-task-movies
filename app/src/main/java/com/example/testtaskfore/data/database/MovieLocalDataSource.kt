package com.example.testtaskfore.data.database

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieLocalDataSource @Inject constructor(
    private val database: AppDatabase
) {
    fun getAllMovies(): Flow<List<MovieEntity>> =
        database.movieDao().getAllMovies()

    suspend fun insertAll(movies: List<MovieEntity>) =
        database.movieDao().insertAll(movies)

    suspend fun deleteAll() =
        database.movieDao().deleteAllMovies()
}