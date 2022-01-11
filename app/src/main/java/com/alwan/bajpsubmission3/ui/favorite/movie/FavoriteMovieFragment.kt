package com.alwan.bajpsubmission3.ui.favorite.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alwan.bajpsubmission3.R
import com.alwan.bajpsubmission3.adapter.MovieAdapter
import com.alwan.bajpsubmission3.data.source.local.entity.MovieEntity
import com.alwan.bajpsubmission3.databinding.FragmentMovieBinding
import com.alwan.bajpsubmission3.ui.detail.DetailActivity
import com.alwan.bajpsubmission3.utils.MarginItemDecoration
import com.alwan.bajpsubmission3.utils.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class FavoriteMovieFragment : Fragment(), MovieAdapter.MovieCallback {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movieViewModel: FavoriteMovieViewModel
    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val movieEntity = movieAdapter.getSwipedData(swipedPosition)
                movieEntity?.let { movieViewModel.setFavoriteMovie(it) }

                val snackBar = Snackbar.make(requireView(), R.string.favorite_remove, Snackbar.LENGTH_LONG)
                snackBar.setAction(R.string.undo) { _ ->
                    movieEntity?.let { movieViewModel.setFavoriteMovie(it) }
                }
                snackBar.show()
            }
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupAdapter()
        setupRvMovie()
    }

    private fun setupViewModel() {
        val factory = ViewModelFactory.getInstance(requireContext())
        movieViewModel = ViewModelProvider(this, factory)[FavoriteMovieViewModel::class.java]
    }

    private fun setupAdapter() {
        movieAdapter = MovieAdapter(this)
        itemTouchHelper.attachToRecyclerView(binding.rvMovie)
        showLoading(true)
        showEmpty(false)

        movieViewModel.getFavoriteMovies().observe(viewLifecycleOwner, {
            if (it != null) {
                showLoading(false)
                if (it.isEmpty()) {
                    showEmpty(true)
                }
                movieAdapter.submitList(it)
            }
        })
    }

    private fun setupRvMovie() {
        with(binding.rvMovie) {
            setHasFixedSize(true)
            addItemDecoration(MarginItemDecoration(16))
            layoutManager = LinearLayoutManager(requireContext())
            adapter = movieAdapter
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBarMovie.visibility = if (state) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun showEmpty(state: Boolean) {
        binding.imgEmptyMovie.visibility = if (state) {
            View.VISIBLE
        } else {
            View.GONE
        }

        binding.tvEmptyMovie.visibility = if (state) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    override fun onMovieClick(movieEntity: MovieEntity) {
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_ID, movieEntity.id)
        intent.putExtra(DetailActivity.EXTRA_TYPE, 0)
        startActivity(intent)
    }
}