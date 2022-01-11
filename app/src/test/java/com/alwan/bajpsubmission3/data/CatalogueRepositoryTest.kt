package com.alwan.bajpsubmission3.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.alwan.bajpsubmission3.data.source.local.LocalDataSource
import com.alwan.bajpsubmission3.data.source.local.entity.MovieEntity
import com.alwan.bajpsubmission3.data.source.local.entity.TvShowEntity
import com.alwan.bajpsubmission3.data.source.remote.RemoteDataSource
import com.alwan.bajpsubmission3.utils.*
import com.alwan.bajpsubmission3.utils.SortUtils.VOTE_BEST
import com.alwan.bajpsubmission3.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class CatalogueRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)
    private val catalogueRepository = FakeCatalogueRepository(remote, local, appExecutors)
    private val moviesResponse = DummyCatalogue.getRemoteMovies()
    private val movieId = moviesResponse[0].id.toString()
    private val movieDetail = DummyCatalogue.getRemoteMovies()[0]
    private val tvShowsResponse = DummyCatalogue.getRemoteTvShows()
    private val tvShowId = tvShowsResponse[0].id.toString()
    private val tvShowDetail = DummyCatalogue.getRemoteTvShows()[0]

    @Test
    fun getMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getAllMovies(VOTE_BEST)).thenReturn(dataSourceFactory)
        catalogueRepository.getMovies(VOTE_BEST)

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DummyCatalogue.getMovies()))
        verify(local).getAllMovies(VOTE_BEST)
        assertNotNull(movieEntities)
        assertEquals(moviesResponse.size, movieEntities.data?.size)
    }

    @Test
    fun getDetailMovie() {
        val dummyDetailMovie = MutableLiveData<MovieEntity>()
        dummyDetailMovie.value = DummyCatalogue.getMovies()[0]
        `when`(local.getMovieById(movieId.toInt())).thenReturn(dummyDetailMovie)

        val movieDetailEntity = LiveDataTestUtil.getValue(catalogueRepository.getDetailMovie(movieId))
        verify(local).getMovieById(movieId.toInt())
        assertNotNull(movieDetailEntity)
        assertEquals(movieDetail.id, movieDetailEntity.data?.id)
        assertEquals(movieDetail.title, movieDetailEntity.data?.title)
        assertEquals(movieDetail.overview, movieDetailEntity.data?.overview)
        assertEquals(movieDetail.genres.toGenreString(), movieDetailEntity.data?.genres)
        assertEquals(movieDetail.posterPath, movieDetailEntity.data?.posterPath)
        assertEquals(movieDetail.voteAverage, movieDetailEntity.data?.voteAverage)
    }

    @Test
    fun getTvShows() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getAllTvShows(VOTE_BEST)).thenReturn(dataSourceFactory)
        catalogueRepository.getTvShows(VOTE_BEST)

        val tvShowEntities = Resource.success(PagedListUtil.mockPagedList(DummyCatalogue.getTvShows()))
        verify(local).getAllTvShows(VOTE_BEST)
        assertNotNull(tvShowEntities)
        assertEquals(tvShowsResponse.size, tvShowEntities.data?.size)
    }

    @Test
    fun getDetailTvShow() {
        val dummyDetailTvShow = MutableLiveData<TvShowEntity>()
        dummyDetailTvShow.value = DummyCatalogue.getTvShows()[0]
        `when`(local.getTvShowById(tvShowId.toInt())).thenReturn(dummyDetailTvShow)

        val tvShowDetailEntity = LiveDataTestUtil.getValue(catalogueRepository.getDetailTvShow(tvShowId))
        verify(local).getTvShowById(tvShowId.toInt())
        assertNotNull(tvShowDetailEntity)
        assertEquals(tvShowDetail.id, tvShowDetailEntity.data?.id)
        assertEquals(tvShowDetail.name, tvShowDetailEntity.data?.name)
        assertEquals(tvShowDetail.overview, tvShowDetailEntity.data?.overview)
        assertEquals(tvShowDetail.genres.toGenreString(), tvShowDetailEntity.data?.genres)
        assertEquals(tvShowDetail.posterPath, tvShowDetailEntity.data?.posterPath)
        assertEquals(tvShowDetail.voteAverage, tvShowDetailEntity.data?.voteAverage)
    }
}