package com.alwan.bajpsubmission3.ui.discover.movie

import androidx.lifecycle.ViewModel
import com.alwan.bajpsubmission3.data.CatalogueRepository

class MovieViewModel(private val catalogueRepository: CatalogueRepository) : ViewModel() {
    fun getMovies(sort: String) = catalogueRepository.getMovies(sort)
}