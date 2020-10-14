package com.arjun.moviesbox.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arjun.moviesbox.BuildConfig
import com.arjun.moviesbox.GlideApp
import com.arjun.moviesbox.R
import com.arjun.moviesbox.databinding.MovieItemBinding
import com.arjun.moviesbox.model.Movie

class MovieAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MovieViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Movie>) {
        differ.submitList(list)
    }

    inner class MovieViewHolder(
        private val binding: MovieItemBinding,
        private val interaction: Interaction?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movie) {

            binding.apply {
                movieTitle.text = item.title
                GlideApp.with(itemView)
                    .load(BuildConfig.TMDB_IMAGE_BASE_URL + item.posterPath)
                    .placeholder(R.drawable.ic_undraw_images)
                    .error(R.drawable.ic_undraw_404)
                    .centerCrop()
                    .into(moviePoster)
                root.setOnClickListener {
                    interaction?.onItemSelected(adapterPosition, item = item)
                }
            }
        }
    }


    interface Interaction {
        fun onItemSelected(position: Int, item: Movie)
    }

}

