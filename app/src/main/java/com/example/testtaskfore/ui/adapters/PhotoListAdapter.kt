package com.example.testtaskfore.ui.adapters

import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskfore.data.model.UnsplashPhoto

class PhotoListAdapter(
    private val onItemClicked: (UnsplashPhoto) -> Unit
) : ListAdapter<UnsplashPhoto, PhotoListAdapter.ListViewHolder>(DiffCallback) {

    class ListViewHolder(private var binding: ItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(photo: UnsplashPhoto)
        }
}