package com.alwan.bajpsubmission3.data

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.alwan.bajpsubmission3.data.source.local.LocalDataSource
import com.alwan.bajpsubmission3.data.source.local.entity.*
import com.alwan.bajpsubmission3.data.source.remote.ApiResponse
import com.alwan.bajpsubmission3.data.source.remote.RemoteDataSource
import com.alwan.bajpsubmission3.data.source.remote.response.movie.MovieDetailResponse
import com.alwan.bajpsubmission3.data.source.remote.response.tvshow.TvShowDetailResponse
import com.alwan.bajpsubmission3.utils.AppExecutors
import com.alwan.bajpsubmission3.utils.toGenreString
import com.alwan.bajpsubmission3.vo.Resource

class CatalogueRepository private constructor(
    private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) :
    CatalogueDataSource {

    override fun getMovies(sort: String): LiveData<Resource<PagedList<MovieEntity>>> {
        return object :
            NetworkBoundResource<PagedList<MovieEntity>, ArrayList<MovieDetailResponse>>(
                appExecutors
            ) {
            override fun loadFromDb(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()

                return LivePagedListBuilder(localDataSource.getAllMovies(sort), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<ArrayList<MovieDetailResponse>>> =
                remoteDataSource.getMovies()

            override fun saveCallResult(data: ArrayList<MovieDetailResponse>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in data) {
                    with(response) {
                        val movie = MovieEntity(
                            id,
                            title,
                            overview,
                            genres.toGenreString(),
                            voteAverage,
                            posterPath,
                            false,
                        )
                        movieList.add(movie)
                    }
                }
                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getDetailMovie(movieId: String): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, MovieDetailResponse>(appExecutors) {
            override fun loadFromDb(): LiveData<MovieEntity> =
                localDataSource.getMovieById(movieId.toInt())

            override fun shouldFetch(data: MovieEntity?): Boolean =
                data != null && data.genres == ""

            override fun createCall(): LiveData<ApiResponse<MovieDetailResponse>> =
                remoteDataSource.getDetailMovie(movieId)

            override fun saveCallResult(data: MovieDetailResponse) {

                with(data) {
                    val movie = MovieEntity(
                        id,
                        title,
                        overview,
                        genres.toGenreString(),
                        voteAverage,
                        posterPath,
                        false,
                    )
                    localDataSource.updateMovie(movie, false)
                }
            }
        }.asLiveData()
    }

    override fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        return LivePagedListBuilder(localDataSource.getFavMovies(), config).build()
    }

    override fun setFavoriteMovie(movie: MovieEntity, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteMovie(movie, state)
        }
    }

    override fun getTvShows(sort: String): LiveData<Resource<PagedList<TvShowEntity>>> {
        return object :
            NetworkBoundResource<PagedList<TvShowEntity>, ArrayList<TvShowDetailResponse>>(
                appExecutors
            ) {
            override fun loadFromDb(): LiveData<PagedList<TvShowEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()

                return LivePagedListBuilder(localDataSource.getAllTvShows(sort), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<ArrayList<TvShowDetailResponse>>> =
                remoteDataSource.getTvShows()

            override fun saveCallResult(data: ArrayList<TvShowDetailResponse>) {
                val tvShowList = ArrayList<TvShowEntity>()
                for (response in data) {
                    with(response) {
                        val tvShow = TvShowEntity(
                            id,
                            name,
                            overview,
                            genres.toGenreString(),
                            voteAverage,
                            posterPath,
                            false,
                        )
                        tvShowList.add(tvShow)
                    }
                }
                localDataSource.insertTvShows(tvShowList)
            }
        }.asLiveData()
    }

    override fun getDetailTvShow(tvShowId: String): LiveData<Resource<TvShowEntity>> {
        return object : NetworkBoundResource<TvShowEntity, TvShowDetailResponse>(appExecutors) {
            override fun loadFromDb(): LiveData<TvShowEntity> =
                localDataSource.getTvShowById(tvShowId.toInt())

            override fun shouldFetch(data: TvShowEntity?): Boolean =
                data != null && data.genres == ""

            override fun createCall(): LiveData<ApiResponse<TvShowDetailResponse>> =
                remoteDataSource.getDetailTvShow(tvShowId)

            override fun saveCallResult(data: TvShowDetailResponse) {
                with(data) {
                    val tvShow = TvShowEntity(
                        id,
                        name,
                        overview,
                        genres.toGenreString(),
                        voteAverage,
                        posterPath,
                        false,
                    )
                    localDataSource.updateTvShow(tvShow, false)
                }
            }
        }.asLiveData()
    }

    override fun getFavoriteTvShows(): LiveData<PagedList<TvShowEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        return LivePagedListBuilder(localDataSource.getFavTvShows(), config).build()
    }

    override fun setFavoriteTvShow(tvShow: TvShowEntity, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteTvShow(tvShow, state)
        }
    }

    companion object {
        @Volatile
        private var instance: CatalogueRepository? = null
        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): CatalogueRepository =
            instance ?: synchronized(this) {
                instance ?: CatalogueRepository(remoteData, localData, appExecutors)
            }
    }
}