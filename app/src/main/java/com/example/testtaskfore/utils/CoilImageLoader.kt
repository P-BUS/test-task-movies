package com.example.testtaskfore.utils

import android.widget.ImageView
import androidx.core.net.toUri
import coil.load
import com.example.testtaskfore.R

object CoilImageLoader {
    fun loadImage(imageView: ImageView, imageEndpoint: String) {
        val imageUrl = "https://image.tmdb.org/t/p/w500$imageEndpoint"

        imageUrl.let {
            val imageUri = imageUrl.toUri().buildUpon().scheme("https").build()
            imageView.load(imageUri) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }
        }
    }
}