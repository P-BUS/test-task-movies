package com.example.testtaskfore.ui

import androidx.lifecycle.ViewModel
import com.example.testtaskfore.data.model.UnsplashPhoto
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class PhotoViewModel : ViewModel() {


    fun updateCurrentBook(book: UnsplashPhoto) {
        _currentBook.value = book
    }

}