package com.alwan.bajpsubmission3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alwan.bajpsubmission3.R
import com.alwan.bajpsubmission3.data.source.local.entity.TvShowEntity
import com.alwan.bajpsubmission3.databinding.ItemCatalogueBinding
import com.alwan.bajpsubmission3.utils.loadImage

class TvShowAdapter(private val callback: TvShowCallback) :
    PagedListAdapter<TvShowEntity, TvShowAdapter.TvShowViewHolder>(DIFF_CALLBACK) {

    interface TvShowCallback {
        fun onTvShowClick(tvShowEntity: TvShowEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val itemCatalogueBinding =
            ItemCatalogueBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(itemCatalogueBinding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    fun getSwipedData(swipedPosition: Int): TvShowEntity? = getItem(swipedPosition)

    inner class TvShowViewHolder(private val binding: ItemCatalogueBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShowEntity: TvShowEntity) {
            with(binding) {
                tvTitleCatalogue.text = tvShowEntity.name
                tvScoreCatalogue.text = tvShowEntity.voteAverage.toString()
                tvShowEntity.posterPath?.let { imgPosterCatalogue.loadImage(it) }
                    ?: imgPosterCatalogue.setImageResource(
                        R.drawable.catalogue_placeholder
                    )
                root.setOnClickListener { callback.onTvShowClick(tvShowEntity) }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowEntity>() {
            override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}