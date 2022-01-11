package com.alwan.bajpsubmission3.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.alwan.bajpsubmission3.data.source.local.entity.MovieEntity
import com.alwan.bajpsubmission3.data.source.local.entity.TvShowEntity
import com.alwan.bajpsubmission3.vo.Resource

interface CatalogueDataSource {
    fun getMovies(sort: String): LiveData<Resource<PagedList<MovieEntity>>>
    fun getDetailMovie(movieId: String): LiveData<Resource<MovieEntity>>
    fun getTvShows(sort: String): LiveData<Resource<PagedList<TvShowEntity>>>
    fun getDetailTvShow(tvShowId: String): LiveData<Resource<TvShowEntity>>
    fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>>
    fun setFavoriteMovie(movie: MovieEntity, state: Boolean)
    fun getFavoriteTvShows(): LiveData<PagedList<TvShowEntity>>
    fun setFavoriteTvShow(tvShow: TvShowEntity, state: Boolean)
}