package com.alwan.bajpsubmission3.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.alwan.bajpsubmission3.R
import com.alwan.bajpsubmission3.data.source.local.entity.MovieEntity
import com.alwan.bajpsubmission3.data.source.local.entity.TvShowEntity
import com.alwan.bajpsubmission3.databinding.ActivityDetailBinding
import com.alwan.bajpsubmission3.utils.ViewModelFactory
import com.alwan.bajpsubmission3.utils.loadImage
import com.alwan.bajpsubmission3.vo.Status

class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    private var favoriteState: Boolean? = false
    private var menu: Menu? = null
    private var catalogueType: Int = 0
    private lateinit var movieEntity: MovieEntity
    private lateinit var tvShowEntity: TvShowEntity
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val catalogueId = intent.getIntExtra(EXTRA_ID, 0)
        catalogueType = intent.getIntExtra(EXTRA_TYPE, 0)
        setupViewModel()
        setupDetail(catalogueId)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add_favorite -> {
                when (catalogueType) {
                    0 -> {
                        detailViewModel.setFavoriteMovie(movieEntity)
                        changeFavoriteState(movieEntity.isFavorite)
                    }
                    1 -> {
                        detailViewModel.setFavoriteTvShow(tvShowEntity)
                        changeFavoriteState(tvShowEntity.isFavorite)
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        detailViewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
    }

    private fun setupDetail(id: Int?) {
        showLoading(true)
        enableFavorite(false)
        when (catalogueType) {
            0 -> {
                if (id != null) {
                    detailViewModel.getDetailMovie(id).observe(this, { detailMovie ->
                        when (detailMovie.status) {
                            Status.LOADING -> {
                                showLoading(true)
                                enableFavorite(false)
                            }
                            Status.SUCCESS -> {
                                showLoading(false)
                                detailMovie.data?.let { it -> populateDetailMovie(it) }
                                enableFavorite(true)
                            }
                            Status.ERROR -> {
                                showLoading(false)
                                enableFavorite(false)
                                Toast.makeText(
                                    this,
                                    "Error Loading Detail Movie",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
                }
            }
            1 -> {
                if (id != null) {
                    detailViewModel.getDetailTvShow(id).observe(this, { detailTvShow ->
                        when (detailTvShow.status) {
                            Status.LOADING -> showLoading(true)
                            Status.SUCCESS -> {
                                showLoading(false)
                                detailTvShow.data?.let { it -> populateDetailTvShow(it) }
                            }
                            Status.ERROR -> {
                                showLoading(false)
                                Toast.makeText(
                                    this,
                                    "Error Loading Detail Tv Show",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
                }
            }
        }
    }

    private fun populateDetailMovie(movieEntity: MovieEntity) {
        this.movieEntity = movieEntity
        with(binding) {
            with(movieEntity) {
                tvTitleDetail.text = title
                tvGenreDetail.text = genres
                tvOverviewDetail.text = overview
                tvScoreDetail.text = voteAverage.toString()
                imgPosterDetail.loadImage(posterPath)
                imgPosterDetail.tag = posterPath
                favoriteState = isFavorite
            }
        }
        changeFavoriteState(favoriteState)
    }

    private fun populateDetailTvShow(tvShowEntity: TvShowEntity) {
        this.tvShowEntity = tvShowEntity
        with(binding) {
            with(tvShowEntity) {
                tvTitleDetail.text = name
                tvGenreDetail.text = genres
                tvOverviewDetail.text = overview
                tvScoreDetail.text = voteAverage.toString()
                imgPosterDetail.loadImage(posterPath)
                imgPosterDetail.tag = posterPath
                favoriteState = isFavorite
            }
        }
        changeFavoriteState(favoriteState)
    }

    private fun showLoading(state: Boolean) {
        binding.progressBarDetail.visibility = if (state) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun enableFavorite(state: Boolean) {
        menu?.findItem(R.id.menu_add_favorite)?.isEnabled = state
    }

    private fun changeFavoriteState(state: Boolean?) {
        state?.let {
            if (it) {
                menu?.findItem(R.id.menu_add_favorite)
                    ?.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite))
            } else {
                menu?.findItem(R.id.menu_add_favorite)
                    ?.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border))
            }
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_TYPE = "extra_type"
    }
}