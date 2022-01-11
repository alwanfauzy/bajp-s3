package com.alwan.bajpsubmission3.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.alwan.bajpsubmission3.data.source.local.entity.MovieEntity
import com.alwan.bajpsubmission3.data.source.local.entity.TvShowEntity
import com.alwan.bajpsubmission3.data.source.local.room.CatalogueDao
import com.alwan.bajpsubmission3.utils.SortUtils
import com.alwan.bajpsubmission3.utils.SortUtils.MOVIE_ENTITIES
import com.alwan.bajpsubmission3.utils.SortUtils.TV_SHOW_ENTITIES

class LocalDataSource(private val mCatalogueDao: CatalogueDao) {

    fun getAllMovies(sort: String): DataSource.Factory<Int, MovieEntity> = mCatalogueDao.getMovies(
        SortUtils.getSortedQuery(sort, MOVIE_ENTITIES)
    )

    fun getMovieById(id: Int): LiveData<MovieEntity> = mCatalogueDao.getMovieById(id)

    fun getFavMovies(): DataSource.Factory<Int, MovieEntity> = mCatalogueDao.getFavoriteMovies()

    fun getAllTvShows(sort: String): DataSource.Factory<Int, TvShowEntity> =
        mCatalogueDao.getTvShows(SortUtils.getSortedQuery(sort, TV_SHOW_ENTITIES))

    fun getTvShowById(id: Int): LiveData<TvShowEntity> = mCatalogueDao.getTvShowById(id)

    fun getFavTvShows(): DataSource.Factory<Int, TvShowEntity> = mCatalogueDao.getFavoriteTvShows()

    fun insertMovies(movies: List<MovieEntity>) = mCatalogueDao.insertMovies(movies)

    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        movie.isFavorite = newState
        mCatalogueDao.updateMovie(movie)
    }

    fun updateMovie(movie: MovieEntity, newState: Boolean) {
        movie.isFavorite = newState
        mCatalogueDao.updateMovie(movie)
    }

    fun insertTvShows(tvShows: List<TvShowEntity>) = mCatalogueDao.insertTvShows(tvShows)

    fun setFavoriteTvShow(tvShow: TvShowEntity, newState: Boolean) {
        tvShow.isFavorite = newState
        mCatalogueDao.updateTvShow(tvShow)
    }

    fun updateTvShow(tvShow: TvShowEntity, newState: Boolean) {
        tvShow.isFavorite = newState
        mCatalogueDao.updateTvShow(tvShow)
    }

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(catalogueDao: CatalogueDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(catalogueDao)
    }
}