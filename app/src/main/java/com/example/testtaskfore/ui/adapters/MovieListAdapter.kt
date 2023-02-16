package com.example.testtaskfore.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskfore.data.model.MovieModel
import com.example.testtaskfore.databinding.ItemViewBinding
import com.example.testtaskfore.utils.CoilImageLoader

class MovieListAdapter(
    private val onItemClicked: (MovieModel) -> Unit
) : ListAdapter<MovieModel, MovieListAdapter.ListViewHolder>(DiffCallback) {

    class ListViewHolder(private var binding: ItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(movie: MovieModel) {
                binding.movieName.text = movie.title
                CoilImageLoader.loadImage(binding.posterImage, movie.posterPath)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val viewHolder = ListViewHolder(
            ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem == newItem
        }
    }

}