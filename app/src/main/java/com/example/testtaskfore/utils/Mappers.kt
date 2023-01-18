package com.example.testtaskfore.utils

import com.example.testtaskfore.data.database.PhotosEntity
import com.example.testtaskfore.data.model.UnsplashPhoto

fun List<PhotosEntity>.asDomainModel(): List<UnsplashPhoto> {
    return map {
        UnsplashPhoto(
        blurHash = it.blurHash,
        color = it.color,
        createdAt = it.createdAt,
        description = it.description,
        height = it.height,
        id = it.id,
        likedByUser = it.likedByUser,
        likes = it.likes,
        links = it.links,
        updatedAt = it.updatedAt,
        urls = it.urls,
        user = it.user,
        width = it.width
        )
    }
}

fun List<UnsplashPhoto>.asDatabaseModel(): List<PhotosEntity> {
    return map {
        PhotosEntity(
            blurHash = it.blurHash,
            color = it.color,
            createdAt = it.createdAt,
            description = it.description,
            height = it.height,
            id = it.id,
            likedByUser = it.likedByUser,
            likes = it.likes,
            links = it.links,
            updatedAt = it.updatedAt,
            urls = it.urls,
            user = it.user,
            width = it.width
        )
    }
}