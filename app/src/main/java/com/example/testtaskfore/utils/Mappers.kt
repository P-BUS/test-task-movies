package com.example.testtaskfore.utils

import com.example.testtaskfore.data.database.MovieEntity
import com.example.testtaskfore.data.model.Movie
import com.example.testtaskfore.data.model.MovieModel


object  Mappers {

    fun List<MovieEntity>.asDomainModel(): List<MovieModel> {
        return map {
            MovieModel(
                adult = it.adult,
                backdropPath = it.backdropPath,
                id = it.id,
                mediaType = it.mediaType,
                originalLanguage = it.originalLanguage,
                originalTitle = it.originalTitle,
                overview = it.overview,
                popularity = it.popularity,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                title = it.title,
                video = it.video,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount
            )
        }
    }

    fun List<Movie>.fromNetworkToDatabaseModel(): List<MovieEntity> {
        return map {
            MovieEntity(
                adult = it.adult,
                backdropPath = it.backdropPath,
                id = it.id,
                mediaType = it.mediaType,
                originalLanguage = it.originalLanguage,
                originalTitle = it.originalTitle,
                overview = it.overview,
                popularity = it.popularity,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                title = it.title,
                video = it.video,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount
            )
        }
    }

    fun List<MovieModel>.fromDomainToDatabaseModel(): List<MovieEntity> {
        return map {
            MovieEntity(
                adult = it.adult,
                backdropPath = it.backdropPath,
                id = it.id,
                mediaType = it.mediaType,
                originalLanguage = it.originalLanguage,
                originalTitle = it.originalTitle,
                overview = it.overview,
                popularity = it.popularity,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                title = it.title,
                video = it.video,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount
            )
        }
    }
}

