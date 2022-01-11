package com.alwan.bajpsubmission3.ui.detail

import androidx.lifecycle.ViewModel
import com.alwan.bajpsubmission3.data.CatalogueRepository
import com.alwan.bajpsubmission3.data.source.local.entity.MovieEntity
import com.alwan.bajpsubmission3.data.source.local.entity.TvShowEntity

class DetailViewModel(private val catalogueRepository: CatalogueRepository) : ViewModel() {
    fun getDetailMovie(id : Int) = catalogueRepository.getDetailMovie(id.toString())

    fun getDetailTvShow(id: Int) = catalogueRepository.getDetailTvShow(id.toString())

    fun setFavoriteMovie(movieEntity: MovieEntity){
        movieEntity.isFavorite?.let {
            catalogueRepository.setFavoriteMovie(movieEntity, !it)
        }
    }

    fun setFavoriteTvShow(tvShowEntity: TvShowEntity){
        tvShowEntity.isFavorite?.let {
            catalogueRepository.setFavoriteTvShow(tvShowEntity, !it)
        }
    }
}