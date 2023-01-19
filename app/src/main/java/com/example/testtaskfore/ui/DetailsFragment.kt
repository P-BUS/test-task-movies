package com.example.testtaskfore.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.testtaskfore.databinding.DetailsFragmentBinding
import com.example.testtaskfore.utils.CoilImageLoader

class DetailsFragment : Fragment() {
    private val sharedViewModel: PhotoViewModel by activityViewModels()
    private lateinit var binding: DetailsFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindPhoto()
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
        }
    }

}