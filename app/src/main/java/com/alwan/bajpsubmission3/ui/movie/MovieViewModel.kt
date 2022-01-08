package com.alwan.bajpsubmission3.ui.movie

import androidx.lifecycle.ViewModel
import com.alwan.bajpsubmission3.data.CatalogueRepository

class MovieViewModel(private val catalogueRepository: CatalogueRepository) : ViewModel() {
    fun getMovies() = catalogueRepository.getMovies()
}