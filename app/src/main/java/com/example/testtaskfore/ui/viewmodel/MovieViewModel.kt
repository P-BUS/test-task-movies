package com.example.testtaskfore.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtaskfore.data.model.MovieModel
import com.example.testtaskfore.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

const val TAG = "MovieViewModel"

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    // movies stream form database
    val movies: StateFlow<List<MovieModel>> =
        repository.movies
            .retry(3) { e ->
                (e is IOException).also { if (it) delay(1000) }
            }
            .stateIn(
                scope = viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                initialValue = listOf()
            )

    private var _currentMovie = MutableSharedFlow<MovieModel>(1, 0, BufferOverflow.DROP_OLDEST)
    val currentMovie: SharedFlow<MovieModel> = _currentMovie.asSharedFlow()

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                repository.refreshMovies()
            } catch (networkError: IOException) {
                Log.e(TAG, "IO Exception $networkError, you might not have internet connection")
            }
        }
    }

    fun updateCurrentMovie(movie: MovieModel) {
        viewModelScope.launch {
            _currentMovie.emit(movie)
        }
    }
}