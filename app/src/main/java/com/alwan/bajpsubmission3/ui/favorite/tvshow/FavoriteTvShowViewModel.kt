package com.alwan.bajpsubmission3.ui.favorite.tvshow

import androidx.lifecycle.ViewModel
import com.alwan.bajpsubmission3.data.CatalogueRepository
import com.alwan.bajpsubmission3.data.source.local.entity.TvShowEntity

class FavoriteTvShowViewModel(private val catalogueRepository: CatalogueRepository) : ViewModel() {
    fun getFavoriteTvShows() = catalogueRepository.getFavoriteTvShows()

    fun setFavoriteTvShow(tvShowEntity: TvShowEntity){
        tvShowEntity.isFavorite?.let {
            catalogueRepository.setFavoriteTvShow(tvShowEntity, !it)
        }
    }
}