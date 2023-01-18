package com.example.testtaskfore.data.repository

import com.example.testtaskfore.data.model.UnsplashPhoto
import com.example.testtaskfore.data.network.PhotoApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotosRepository @Inject constructor(
    private val network: PhotoApiService
) {
    lateinit var listPhotos: List<UnsplashPhoto>


    suspend fun getPhotos() {
        listPhotos = network.getAllPhotos()

    }


}