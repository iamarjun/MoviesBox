package com.arjun.moviesbox.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.arjun.moviesbox.R
import com.arjun.moviesbox.databinding.FragmentDashboardBinding
import com.arjun.moviesbox.ui.MovieAdapter
import com.arjun.moviesbox.util.Resource
import com.arjun.moviesbox.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private val viewModel: DashboardViewModel by viewModels()
    private val binding: FragmentDashboardBinding by viewBinding(FragmentDashboardBinding::bind)
    private val movieAdapter: MovieAdapter by lazy { MovieAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.movieList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = movieAdapter
        }

        viewModel.topRatedMovies.observe(viewLifecycleOwner) {

            binding.progressBar.isVisible = it is Resource.Loading

            when (it) {

                is Resource.Loading -> {
                    Timber.d("loading")
                }
                is Resource.Success -> {
                    Timber.d(it.data.toString())
                    movieAdapter.submitList(it.data)
                }
                is Resource.Error -> {
                    Timber.e(it.exception)
                }
            }
        }

    }
}