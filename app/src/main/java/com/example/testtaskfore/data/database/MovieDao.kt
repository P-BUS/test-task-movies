package com.example.testtaskfore.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies_database")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Upsert
    fun insertAll(movies: List<MovieEntity>)

    @Query("DELETE FROM movies_database")
    fun deleteAllMovies()
}