package com.example.testtaskfore.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtaskfore.data.model.UnsplashPhoto
import com.example.testtaskfore.data.repository.PhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
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

    // TODO: change to StateFlow but what to choose for default value?
    private val _currentPhoto = MutableLiveData<UnsplashPhoto>()
    val currentPhoto: LiveData<UnsplashPhoto> = _currentPhoto

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

    fun updateCurrentPhoto(photo: UnsplashPhoto) {
        _currentPhoto.value = photo
    }

    fun saveLikesInDatabase(id: String, isLiked: Boolean) {
        viewModelScope.launch {
            repository.saveLikesInDatabase(id, isLiked)
        }
    }

}