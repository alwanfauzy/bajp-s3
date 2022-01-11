package com.alwan.bajpsubmission3.ui.discover.tvshow

import androidx.lifecycle.ViewModel
import com.alwan.bajpsubmission3.data.CatalogueRepository

class TvShowViewModel(private val catalogueRepository: CatalogueRepository) : ViewModel() {
    fun getTvShows(sort: String) = catalogueRepository.getTvShows(sort)
}