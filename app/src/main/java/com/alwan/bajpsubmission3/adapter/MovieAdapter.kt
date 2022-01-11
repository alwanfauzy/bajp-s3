package com.alwan.bajpsubmission3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alwan.bajpsubmission3.R
import com.alwan.bajpsubmission3.data.source.local.entity.MovieEntity
import com.alwan.bajpsubmission3.databinding.ItemCatalogueBinding
import com.alwan.bajpsubmission3.utils.loadImage

class MovieAdapter(private val callback: MovieCallback) :
    PagedListAdapter<MovieEntity, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    interface MovieCallback {
        fun onMovieClick(movieEntity: MovieEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemCatalogueBinding =
            ItemCatalogueBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemCatalogueBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    fun getSwipedData(swipedPosition: Int): MovieEntity? = getItem(swipedPosition)

    inner class MovieViewHolder(private val binding: ItemCatalogueBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movieEntity: MovieEntity) {
            with(binding) {
                tvTitleCatalogue.text = movieEntity.title
                tvScoreCatalogue.text = movieEntity.voteAverage.toString()
                movieEntity.posterPath?.let { imgPosterCatalogue.loadImage(it) }
                    ?: imgPosterCatalogue.setImageResource(
                        R.drawable.catalogue_placeholder
                    )
                root.setOnClickListener { callback.onMovieClick(movieEntity) }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}