package com.alwan.bajpsubmission3.ui.detail

import androidx.lifecycle.ViewModel
import com.alwan.bajpsubmission3.data.CatalogueRepository

class DetailViewModel(private val catalogueRepository: CatalogueRepository) : ViewModel() {
    fun getDetailMovie(id : Int) = catalogueRepository.getDetailMovie(id.toString())

    fun getDetailTvShow(id: Int) = catalogueRepository.getDetailTvShow(id.toString())
}