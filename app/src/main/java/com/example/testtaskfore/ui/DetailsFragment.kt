package com.example.testtaskfore.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.example.testtaskfore.R
import com.example.testtaskfore.data.model.UnsplashPhoto
import com.example.testtaskfore.databinding.DetailsFragmentBinding
import com.example.testtaskfore.utils.CoilImageLoader
import com.example.testtaskfore.utils.SnackbarsUtils
import com.google.android.material.snackbar.Snackbar

class DetailsFragment : Fragment() {
    private val sharedViewModel: PhotoViewModel by activityViewModels()
    private lateinit var binding: DetailsFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // binds variables to elements of view
        bindPhoto()
        binding.ivFavorite.setOnClickListener {
            onLikeClicked(sharedViewModel.currentPhoto)
            showSnackbar(binding.root, R.string.snackbar_favorite, Snackbar.LENGTH_SHORT,
                findNavController().navigate(R.id.action_detailsFragment_to_favoriteFragment))
        }
    }

    private fun bindPhoto() {
        with(sharedViewModel.currentPhoto.value) {
            this?.urls?.full?.let {
                CoilImageLoader.loadImage(binding.ivDetailedImage, it)
            }
            this?.description?.let {
                binding.tvImageDescription.text = it
            }
            this?.user?.name?.let {
                binding.tvAuthorName.text = it
            }
            this?.createdAt?.let {
                binding.tvCreatedDate.text = it
            }
            this?.likes?.let {
                binding.tvLikesQuantity.text = it.toString()
            }
            this?.let {
                setLikeImage(it)
            }
        }
    }

    private fun setLikeImage(current_photo: UnsplashPhoto) {
        if (current_photo.likedByUser == true) {
            binding.ivFavorite.setImageResource(R.drawable.baseline_favorite_48)
        } else {
            binding.ivFavorite.setImageResource(R.drawable.baseline_favorite_border_48)
        }
    }

    private fun onLikeClicked(current_photo: LiveData<UnsplashPhoto>) {
       var isLiked = false
        current_photo.value?.likedByUser?.let {
            isLiked = it
        }
        isLiked = !isLiked
        current_photo.value?.id?.let {
            sharedViewModel.saveLikesInDatabase(it, isLiked)
        }

    }

    private fun showSnackbar(view: View, message: Int, length: Int, action: Unit) {
        Snackbar
            .make(view, message, length)
            .setAction(R.string.action_text) {
                action
            }
            .show()
    }

}