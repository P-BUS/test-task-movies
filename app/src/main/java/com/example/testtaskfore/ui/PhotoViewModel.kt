package com.example.testtaskfore.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtaskfore.data.repository.PhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val repository: PhotosRepository
) : ViewModel() {


    init {
        getPhotos()
    }

    val photos = repository.listPhotos


    fun getPhotos() {
        viewModelScope.launch {
            repository.getPhotos()
        }
    }
}