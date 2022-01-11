package com.alwan.bajpsubmission3.ui.discover.tvshow

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
import com.alwan.bajpsubmission3.adapter.TvShowAdapter
import com.alwan.bajpsubmission3.data.source.local.entity.TvShowEntity
import com.alwan.bajpsubmission3.databinding.FragmentTvShowBinding
import com.alwan.bajpsubmission3.ui.detail.DetailActivity
import com.alwan.bajpsubmission3.utils.MarginItemDecoration
import com.alwan.bajpsubmission3.utils.SortUtils.RANDOM
import com.alwan.bajpsubmission3.utils.SortUtils.VOTE_BEST
import com.alwan.bajpsubmission3.utils.SortUtils.VOTE_WORST
import com.alwan.bajpsubmission3.utils.ViewModelFactory
import com.alwan.bajpsubmission3.vo.Resource
import com.alwan.bajpsubmission3.vo.Status

class TvShowFragment : Fragment(), TvShowAdapter.TvShowCallback {
    private var _binding: FragmentTvShowBinding? = null
    private val binding get() = _binding!!
    private var sort = VOTE_BEST
    private lateinit var tvShowAdapter: TvShowAdapter
    private lateinit var tvShowViewModel: TvShowViewModel
    private lateinit var tvShowObserver: Observer<Resource<PagedList<TvShowEntity>>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowBinding.inflate(layoutInflater, container, false)
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
        setupRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        requireActivity().menuInflater.inflate(R.menu.menu_main, menu)
        when(sort){
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
        tvShowViewModel.getTvShows(sort).observe(viewLifecycleOwner, tvShowObserver)

        return super.onOptionsItemSelected(item)
    }

    private fun setupViewModel() {
        val factory = ViewModelFactory.getInstance(requireContext())
        tvShowViewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]
    }

    private fun setupAdapter() {
        tvShowAdapter = TvShowAdapter(this)
        showLoading(true)

        tvShowObserver = Observer<Resource<PagedList<TvShowEntity>>> { tvShows ->
            if (tvShows != null) {
                when (tvShows.status) {
                    Status.LOADING -> showLoading(true)
                    Status.SUCCESS -> {
                        showLoading(false)
                        tvShowAdapter.submitList(tvShows.data)
                    }
                    Status.ERROR -> {
                        showLoading(false)
                        Toast.makeText(context, "Error Loading Tv Show", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        tvShowViewModel.getTvShows(VOTE_BEST).observe(viewLifecycleOwner, tvShowObserver)
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

    override fun onTvShowClick(tvShowEntity: TvShowEntity) {
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_ID, tvShowEntity.id)
        intent.putExtra(DetailActivity.EXTRA_TYPE, 1)
        startActivity(intent)
    }
}