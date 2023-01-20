package com.example.testtaskfore.utils

import com.example.testtaskfore.data.database.PhotosEntity
import com.example.testtaskfore.data.model.UnsplashPhoto

fun List<PhotosEntity>.asDomainModel(): List<UnsplashPhoto> {
    return map {
        UnsplashPhoto(
        id= it.id,
        createdAt = it.createdAt,
        description = it.description,
        altDescription = it.altDescription,
        likedByUser = it.likedByUser,
        likes = it.likes,
        urls = it.urls,
        user = it.user
        )
    }
}

fun List<UnsplashPhoto>.asDatabaseModel(): List<PhotosEntity> {
    return map {
        PhotosEntity(
            id= it.id,
            createdAt = it.createdAt,
            description = it.description,
            altDescription = it.altDescription,
            likedByUser = it.likedByUser,
            likes = it.likes,
            urls = it.urls,
            user = it.user
        )
    }
}