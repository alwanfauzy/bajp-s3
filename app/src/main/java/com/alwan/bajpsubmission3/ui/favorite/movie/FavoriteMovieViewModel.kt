package com.alwan.bajpsubmission3.ui.favorite.movie

import androidx.lifecycle.ViewModel
import com.alwan.bajpsubmission3.data.CatalogueRepository
import com.alwan.bajpsubmission3.data.source.local.entity.MovieEntity

class FavoriteMovieViewModel(private val catalogueRepository: CatalogueRepository) : ViewModel() {
    fun getFavoriteMovies() = catalogueRepository.getFavoriteMovies()

    fun setFavoriteMovie(movieEntity: MovieEntity){
        movieEntity.isFavorite?.let {
            catalogueRepository.setFavoriteMovie(movieEntity, !it)
        }
    }
}