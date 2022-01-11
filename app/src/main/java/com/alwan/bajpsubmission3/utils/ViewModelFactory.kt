package com.alwan.bajpsubmission3.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alwan.bajpsubmission3.data.CatalogueRepository
import com.alwan.bajpsubmission3.di.Injection
import com.alwan.bajpsubmission3.ui.detail.DetailViewModel
import com.alwan.bajpsubmission3.ui.discover.movie.MovieViewModel
import com.alwan.bajpsubmission3.ui.discover.tvshow.TvShowViewModel
import com.alwan.bajpsubmission3.ui.favorite.movie.FavoriteMovieViewModel
import com.alwan.bajpsubmission3.ui.favorite.tvshow.FavoriteTvShowViewModel

class ViewModelFactory private constructor(private val catalogueRepository: CatalogueRepository) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(catalogueRepository) as T
            }
            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> {
                TvShowViewModel(catalogueRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(catalogueRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteTvShowViewModel::class.java) -> {
                FavoriteTvShowViewModel(catalogueRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteMovieViewModel::class.java) -> {
                FavoriteMovieViewModel(catalogueRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}