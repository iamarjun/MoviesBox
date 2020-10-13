package com.arjun.moviesbox.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arjun.moviesbox.BuildConfig
import com.arjun.moviesbox.GlideApp
import com.arjun.moviesbox.R
import com.arjun.moviesbox.databinding.MovieItemBinding
import com.arjun.moviesbox.model.Movie

class MovieAdapter : PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(diffCallback) {

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class MovieViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movie?) {

            binding.apply {
                movieTitle.text = item?.title
                GlideApp.with(itemView)
                    .load(BuildConfig.TMDB_IMAGE_BASE_URL + item?.posterPath)
                    .placeholder(R.drawable.ic_undraw_images)
                    .error(R.drawable.ic_undraw_404)
                    .centerCrop()
                    .into(moviePoster)
                root.setOnClickListener {
                }
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {

            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}