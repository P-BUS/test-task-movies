package com.example.testtaskfore.utils

import android.widget.ImageView
import androidx.core.net.toUri
import coil.load


enum class ImageSize(imageSize: String) {
    S("Small"),
    M("Medium"),
    L("Large")
}

/*
* Load images from web service using Coil
**/
object ImageLoader {
    fun loadImage(imageView: ImageView, imageCode: Int, pictureSize: ImageSize) {
        val imageUrl = "https://covers.openlibrary.org/b/id/$imageCode-$pictureSize.jpg"

        imageUrl.let {
            val imageUri = imageUrl.toUri().buildUpon().scheme("https").build()
            imageView.load(imageUri) {
                placeholder(R.drawable.loading_anim)
                error(R.drawable.ic_broken_image)
            }
        }
    }
}