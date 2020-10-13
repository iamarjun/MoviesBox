package com.arjun.moviesbox.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.arjun.moviesbox.MovieLoadStateAdapter
import com.arjun.moviesbox.MovieViewModel
import com.arjun.moviesbox.R
import com.arjun.moviesbox.databinding.FragmentHomeBinding
import com.arjun.moviesbox.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val viewModel: MovieViewModel by activityViewModels()
    private val binding: FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)
    private val movieAdapter: MovieAdapter by lazy { MovieAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.movieList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = movieAdapter
        }

        movieAdapter.withLoadStateHeaderAndFooter(
            header = MovieLoadStateAdapter { movieAdapter.retry() },
            footer = MovieLoadStateAdapter { movieAdapter.retry() }
        )

        movieAdapter.addLoadStateListener { loadState ->
            // Only show the list if refresh succeeds.
            // Show loading spinner during initial load or refresh.
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails.
//            retry.isVisible = loadState.source.refresh is LoadState.Error

            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    requireContext(),
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        lifecycleScope.launch {
            viewModel.getPopularMovieList.collectLatest {
                movieAdapter.submitData(it)
            }
        }

//        homeViewModel.popularMovies.observe(viewLifecycleOwner) {
//            when (it) {
//
//                is Resource.Loading -> {
//                    Timber.d("loading")
//                }
//                is Resource.Success -> {
//                    Timber.d(it.data.toString())
//                }
//                is Resource.Error -> {
//                    Timber.e(it.exception)
//                }
//            }
//        }

    }
}