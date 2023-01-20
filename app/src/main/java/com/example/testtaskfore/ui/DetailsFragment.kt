package com.example.testtaskfore.ui

import android.content.Intent
import android.net.Uri
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
import com.example.testtaskfore.utils.CoilImageLoader
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


        lifecycleScope.launch {
            sharedViewModel.isLiked
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .distinctUntilChanged()
                .collect { value ->
                    isLiked = value
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
                showSnackbar(binding.root, R.string.snackbar_favorite, Snackbar.LENGTH_SHORT)
                { findNavController().navigate(R.id.action_detailsFragment_to_favoriteFragment) }
            } else {
                showSnackbar(binding.root, R.string.snackbar_no_favorite, Snackbar.LENGTH_SHORT)
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
        binding.ivShare.setImageResource(R.drawable.baseline_share_24)
        currentPhoto.urls?.full?.let {
                CoilImageLoader.loadImage(binding.ivDetailedImage, it)
            }
        currentPhoto.description?.let {
                binding.tvImageDescription.text = it
            }
        currentPhoto.user?.name?.let {
                binding.tvAuthorName.text = it
            }
        currentPhoto.createdAt?.let {
                binding.tvCreatedDate.text = it
            }
        currentPhoto.likes?.let {
                binding.tvLikesQuantity.text = it.toString()
            }
        currentPhoto.likedByUser?.let {liked ->
            setLikeImage(liked)
        }
    }

    private fun setLikeImage(liked: Boolean) {
        if (liked) {
            binding.ivFavorite.setImageResource(R.drawable.baseline_favorite_24)
        } else {
            binding.ivFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
        }
    }

    private fun onLikeClicked(current_photo: SharedFlow<UnsplashPhoto>) {
        isLiked = !isLiked
        lifecycleScope.launch {
            sharedViewModel.currentPhoto
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .distinctUntilChanged()
                .collect { currentPhoto ->
                    sharedViewModel.saveLikesInDatabase(currentPhoto.id, isLiked)
                    sharedViewModel.updateIsLiked(isLiked)
                    currentPhoto.likedByUser?.let { setLikeImage(it) }
                }
        }
    }

    private fun showSnackbar(view: View, message: Int, length: Int, action: () -> Unit) {
        Snackbar
            .make(view, message, length)
            .setAction(R.string.action_text) {
                action()
            }
            .show()
    }

}