package com.example.testtaskfore.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.testtaskfore.R
import com.example.testtaskfore.data.model.UnsplashPhoto
import com.example.testtaskfore.databinding.DetailsFragmentBinding
import com.example.testtaskfore.ui.viewmodel.PhotoViewModel
import com.example.testtaskfore.utils.CoilImageLoader
import com.example.testtaskfore.utils.SnackbarsUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {
    private val sharedViewModel: PhotoViewModel by activityViewModels()
    private lateinit var binding: DetailsFragmentBinding
    private var isLiked: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // disable bottom navigation view
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView?.visibility = View.GONE

        lifecycleScope.launch {
            sharedViewModel.isLiked
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .distinctUntilChanged()
                .collect { value ->
                    isLiked = value
                    setLikeImage(value)
                }
        }

        lifecycleScope.launch {
            sharedViewModel.currentPhoto
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .distinctUntilChanged()
                .collect { currentPhoto ->
                    bindPhoto(currentPhoto)
                }
        }

        binding.ivFavorite.setOnClickListener {
            onLikeClicked(sharedViewModel.currentPhoto)
            if(isLiked) {
                SnackbarsUtils.showActionSnackbar(binding.root, R.string.snackbar_favorite, Snackbar.LENGTH_SHORT)
                { findNavController().navigate(R.id.action_detailsFragment_to_favoriteFragment) }
            } else {
                SnackbarsUtils.showActionSnackbar(binding.root, R.string.snackbar_no_favorite, Snackbar.LENGTH_SHORT)
                { findNavController().navigate(R.id.action_detailsFragment_to_favoriteFragment) }
            }
        }

        binding.ivShare.setOnClickListener {
            lifecycleScope.launch {
                sharedViewModel.currentPhoto
                    .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                    .distinctUntilChanged()
                    .collect { currentPhoto ->
                        sendPhoto(currentPhoto.urls?.full.toString())
                    }
            }
        }
    }

    private fun sendPhoto(url: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
            .setType("image/*")
            .putExtra(Intent.EXTRA_TEXT, url)
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_photo)))
    }
    private fun bindPhoto(currentPhoto: UnsplashPhoto) {
        binding.ivShare.setImageResource(R.drawable.ic_share)
        currentPhoto.urls.full?.let {
                CoilImageLoader.loadImage(binding.ivDetailedImage, it)
            }
        currentPhoto.description?.let {
                binding.tvImageDescription.text = it
            }
        currentPhoto.user.name?.let {
                binding.tvAuthorName.text = it
            }
        binding.tvCreatedDate.text = currentPhoto.createdDateFormatted
        binding.tvLikesQuantity.text = getString(R.string.likes_quantity, currentPhoto.likes)

        currentPhoto.likedByUser?.let {liked ->
            setLikeImage(liked)
        }
    }

    private fun setLikeImage(isLiked: Boolean) {
        if (isLiked) {
            binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
        } else {
            binding.ivFavorite.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    private fun onLikeClicked(current_photo: SharedFlow<UnsplashPhoto>) {
        isLiked = !isLiked
        lifecycleScope.launch {
            current_photo
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .distinctUntilChanged()
                .collect { currentPhoto ->
                    sharedViewModel.saveLikesInDatabase(currentPhoto.id, isLiked)
                    currentPhoto.likedByUser?.let {
                        sharedViewModel.updateIsLiked(it)
                    }
                }
        }
        //sharedViewModel.updateIsLiked(isLiked)
        setLikeImage(isLiked)
    }
}