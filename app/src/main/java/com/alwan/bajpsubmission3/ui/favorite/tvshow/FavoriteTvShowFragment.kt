package com.alwan.bajpsubmission3.ui.favorite.tvshow

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
import com.alwan.bajpsubmission3.adapter.TvShowAdapter
import com.alwan.bajpsubmission3.data.source.local.entity.TvShowEntity
import com.alwan.bajpsubmission3.databinding.FragmentTvShowBinding
import com.alwan.bajpsubmission3.ui.detail.DetailActivity
import com.alwan.bajpsubmission3.utils.MarginItemDecoration
import com.alwan.bajpsubmission3.utils.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class FavoriteTvShowFragment : Fragment(), TvShowAdapter.TvShowCallback {
    private var _binding: FragmentTvShowBinding? = null
    private val binding get() = _binding!!
    private lateinit var tvShowAdapter: TvShowAdapter
    private lateinit var tvShowViewModel: FavoriteTvShowViewModel
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
                val movieEntity = tvShowAdapter.getSwipedData(swipedPosition)
                movieEntity?.let { tvShowViewModel.setFavoriteTvShow(it) }

                val snackBar = Snackbar.make(requireView(), R.string.favorite_remove, Snackbar.LENGTH_LONG)
                snackBar.setAction(R.string.undo) { _ ->
                    movieEntity?.let { tvShowViewModel.setFavoriteTvShow(it) }
                }
                snackBar.show()
            }
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowBinding.inflate(layoutInflater, container, false)

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
        setupRecyclerView()
    }

    private fun setupViewModel() {
        val factory = ViewModelFactory.getInstance(requireContext())
        tvShowViewModel = ViewModelProvider(this, factory)[FavoriteTvShowViewModel::class.java]
    }

    private fun setupAdapter() {
        tvShowAdapter = TvShowAdapter(this)
        itemTouchHelper.attachToRecyclerView(binding.rvTvShow)
        showLoading(true)
        showEmpty(false)

        tvShowViewModel.getFavoriteTvShows().observe(viewLifecycleOwner, {
            if (it != null) {
                showLoading(false)
                if (it.isEmpty()) {
                    showEmpty(true)
                }
                tvShowAdapter.submitList(it)
            }
        })
    }

    private fun setupRecyclerView() {
        with(binding.rvTvShow) {
            setHasFixedSize(true)
            addItemDecoration(MarginItemDecoration(16))
            layoutManager = LinearLayoutManager(requireContext())
            adapter = tvShowAdapter
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBarTvShow.visibility = if (state) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun showEmpty(state: Boolean) {
        binding.imgEmptyTvShow.visibility = if (state) {
            View.VISIBLE
        } else {
            View.GONE
        }

        binding.tvEmptyTvShow.visibility = if (state) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    override fun onTvShowClick(tvShowEntity: TvShowEntity) {
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_ID, tvShowEntity.id)
        intent.putExtra(DetailActivity.EXTRA_TYPE, 1)
        startActivity(intent)
    }
}