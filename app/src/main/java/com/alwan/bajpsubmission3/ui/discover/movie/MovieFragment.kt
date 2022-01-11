package com.alwan.bajpsubmission3.ui.discover.movie

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.alwan.bajpsubmission3.R
import com.alwan.bajpsubmission3.adapter.MovieAdapter
import com.alwan.bajpsubmission3.data.source.local.entity.MovieEntity
import com.alwan.bajpsubmission3.databinding.FragmentMovieBinding
import com.alwan.bajpsubmission3.ui.detail.DetailActivity
import com.alwan.bajpsubmission3.utils.MarginItemDecoration
import com.alwan.bajpsubmission3.utils.SortUtils.RANDOM
import com.alwan.bajpsubmission3.utils.SortUtils.VOTE_BEST
import com.alwan.bajpsubmission3.utils.SortUtils.VOTE_WORST
import com.alwan.bajpsubmission3.utils.ViewModelFactory
import com.alwan.bajpsubmission3.vo.Resource
import com.alwan.bajpsubmission3.vo.Status

class MovieFragment : Fragment(), MovieAdapter.MovieCallback {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private var sort = VOTE_BEST
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieObserver: Observer<Resource<PagedList<MovieEntity>>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        requireActivity().menuInflater.inflate(R.menu.menu_main, menu)
        when (sort) {
            VOTE_BEST -> menu.findItem(R.id.menu_sort_best).isChecked = true
            VOTE_WORST -> menu.findItem(R.id.menu_sort_worst).isChecked = true
            RANDOM -> menu.findItem(R.id.menu_sort_random).isChecked = true
        }

        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_sort_best -> sort = VOTE_BEST
            R.id.menu_sort_worst -> sort = VOTE_WORST
            R.id.menu_sort_random -> sort = RANDOM
            R.id.menu_language -> startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

        item.isChecked = true
        movieViewModel.getMovies(sort).observe(viewLifecycleOwner, movieObserver)

        return super.onOptionsItemSelected(item)
    }

    private fun setupViewModel() {
        val factory = ViewModelFactory.getInstance(requireContext())
        movieViewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
    }

    private fun setupAdapter() {
        movieAdapter = MovieAdapter(this)
        showLoading(true)

        movieObserver = Observer<Resource<PagedList<MovieEntity>>> { movies ->
            if (movies != null) {
                when (movies.status) {
                    Status.LOADING -> showLoading(true)
                    Status.SUCCESS -> {
                        showLoading(false)
                        movieAdapter.submitList(movies.data)
                    }
                    Status.ERROR -> {
                        showLoading(false)
                        Toast.makeText(context, "Error Loading Movies", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        movieViewModel.getMovies(VOTE_BEST).observe(viewLifecycleOwner, movieObserver)
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

    override fun onMovieClick(movieEntity: MovieEntity) {
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_ID, movieEntity.id)
        intent.putExtra(DetailActivity.EXTRA_TYPE, 0)
        startActivity(intent)
    }
}