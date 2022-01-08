package com.alwan.bajpsubmission3.ui.tvshow

import androidx.lifecycle.ViewModel
import com.alwan.bajpsubmission3.data.CatalogueRepository

class TvShowViewModel(private val catalogueRepository: CatalogueRepository) : ViewModel() {
    fun getTvShows() = catalogueRepository.getTvShows()
}