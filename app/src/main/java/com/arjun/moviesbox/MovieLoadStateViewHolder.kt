package com.arjun.moviesbox

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.arjun.moviesbox.databinding.MovieLoadStateFooterViewItemBinding

class MovieLoadStateViewHolder(
    private val binding: MovieLoadStateFooterViewItemBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = (loadState is LoadState.Loading)
        binding.retryButton.isVisible = (loadState !is LoadState.Loading)
        binding.errorMsg.isVisible = (loadState !is LoadState.Loading)
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): MovieLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_load_state_footer_view_item, parent, false)
            val binding = MovieLoadStateFooterViewItemBinding.bind(view)
            return MovieLoadStateViewHolder(binding, retry)
        }
    }
}