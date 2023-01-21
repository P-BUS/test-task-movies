package com.example.testtaskfore.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtaskfore.data.model.UnsplashPhoto
import com.example.testtaskfore.data.repository.PhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

const val TAG = "PhotoViewModel"

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val repository: PhotosRepository
) : ViewModel() {

    // photos steam form database
    val photos: StateFlow<List<UnsplashPhoto>> =
        repository.photos
            // if exception caught retry 3 times on any IOException but also introduce delay 1sec if retrying
            .retry(3) { e ->
                (e is IOException).also { if (it) delay(1000) }
            }
            .stateIn(
                scope = viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                initialValue = listOf()
            )

    // favorites steam from database
    val favoritePhotos: StateFlow<List<UnsplashPhoto>> =
        repository.favoriteFhotos
            // if exception caught retry 3 times on any IOException but also introduce delay 1sec if retrying
            .retry(3) { e ->
                (e is IOException).also { if (it) delay(1000) }
            }
            .stateIn(
                scope = viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                initialValue = listOf()
            )


    private var _currentPhoto = MutableSharedFlow<UnsplashPhoto>(
        replay = 1,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val currentPhoto: SharedFlow<UnsplashPhoto> = _currentPhoto.asSharedFlow()

    private val _searchQuery = MutableStateFlow("french bulldogs")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()


    private val _isLiked = MutableStateFlow(false)
    val isLiked: StateFlow<Boolean> = _isLiked.asStateFlow()

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                repository.refreshPhotos()
            } catch (networkError: IOException) {
                Log.e(TAG, "IO Exception $networkError, you might not have internet connection")
            }
        }
    }

    private fun refreshSearchDataFromRepository() {
        viewModelScope.launch {
            try {
                repository.refreshSearchPhotos()
            } catch (networkError: IOException) {
                Log.e(TAG, "IO Exception $networkError, you might not have internet connection")
            }
        }
    }

    fun updateSearchQuery(searchQuery: String) {
        viewModelScope.launch {
            _searchQuery.emit(searchQuery)
        }
    }

    fun updateCurrentPhoto(photo: UnsplashPhoto) {
        viewModelScope.launch {
            _currentPhoto.emit(photo)
        }
    }

    fun updateIsLiked(liked: Boolean) {
        _isLiked.value = liked
    }

    fun saveLikesInDatabase(id: String, isLiked: Boolean) {
        viewModelScope.launch {
            repository.saveLikesInDatabase(id, isLiked)
        }
    }

}