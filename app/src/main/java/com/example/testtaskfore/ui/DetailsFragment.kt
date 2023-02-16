package com.example.testtaskfore.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.testtaskfore.data.model.MovieModel
import com.example.testtaskfore.databinding.DetailsFragmentBinding
import com.example.testtaskfore.ui.viewmodel.MovieViewModel
import com.example.testtaskfore.utils.CoilImageLoader
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {
    private val sharedViewModel: MovieViewModel by activityViewModels()
    private lateinit var binding: DetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            sharedViewModel.currentMovie
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .distinctUntilChanged()
                .collect { currentMovie ->
                    bindMovie(currentMovie)
                }
        }
    }

    private fun bindMovie(currentMovie: MovieModel) {
        CoilImageLoader.loadImage(binding.ivDetailedImage, currentMovie.backdropPath)
        binding.tvMovieTitle.text = currentMovie.title
        binding.tvImageDescription.text = currentMovie.overview
        binding.tvRating.text = currentMovie.voteAverage.toString()
    }

}