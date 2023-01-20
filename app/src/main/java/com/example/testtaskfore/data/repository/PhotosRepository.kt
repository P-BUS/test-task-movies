package com.example.testtaskfore.data.repository

import com.example.testtaskfore.data.database.AppDatabase
import com.example.testtaskfore.data.model.UnsplashPhoto
import com.example.testtaskfore.data.network.PhotoApiService
import com.example.testtaskfore.utils.asDatabaseModel
import com.example.testtaskfore.utils.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotosRepository @Inject constructor(
    private val network: PhotoApiService,
    private val database: AppDatabase,
    private val searchQuery: String
) {

    val photos: Flow<List<UnsplashPhoto>> =
        database.photosDao().getAllPhotos()
            .map { it.asDomainModel() }

    val favoriteFhotos: Flow<List<UnsplashPhoto>> =
        database.photosDao().getAllFavoritePhotos(true)
            .map { it.asDomainModel() }

    suspend fun refreshPhotos() {
        withContext(Dispatchers.IO) {
            // Retrieve photos from network
            // TODO: to add safe response handling if will be time
            val listPhotos: List<UnsplashPhoto> = network.getPhotos()
            // Update database
            database.photosDao().insertAll(listPhotos.asDatabaseModel())
        }
    }

    suspend fun refreshSearchPhotos() {
        withContext(Dispatchers.IO) {
            // Retrieve search photos from network
            // TODO: to add safe response handling if will be time
            val listSearchPhotos: List<UnsplashPhoto> = network.getSearchPhotos(searchQuery)
            // Update database if the search result is success
            if (listSearchPhotos.isNullOrEmpty()) {
                database.photosDao().apply {
                    deleteAllPhotos()
                    insertAll(listSearchPhotos.asDatabaseModel())
                }
            }
        }
    }

    suspend fun saveLikesInDatabase(id: String, isLiked: Boolean) {
        withContext(Dispatchers.IO) {
            database.photosDao().saveLikesInDatabase(id, isLiked)
        }
    }
}