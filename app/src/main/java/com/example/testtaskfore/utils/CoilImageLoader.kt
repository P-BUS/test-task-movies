package com.example.testtaskfore.utils

import android.widget.ImageView
import androidx.core.net.toUri
import coil.load
import com.example.testtaskfore.R

object CoilImageLoader {
    fun loadImage(imageView: ImageView, imageUrl: String) {
        imageUrl.let {
            val imageUri = imageUrl.toUri().buildUpon().scheme("https").build()
            imageView.load(imageUri) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }
        }
    }
}