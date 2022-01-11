package com.alwan.bajpsubmission3.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.alwan.bajpsubmission3.data.CatalogueRepository
import com.alwan.bajpsubmission3.data.source.local.entity.MovieEntity
import com.alwan.bajpsubmission3.data.source.local.entity.TvShowEntity
import com.alwan.bajpsubmission3.utils.DummyCatalogue
import com.alwan.bajpsubmission3.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogueRepository: CatalogueRepository

    @Mock
    private lateinit var movieObserver: Observer<Resource<MovieEntity>>

    @Mock
    private lateinit var tvShowObserver: Observer<Resource<TvShowEntity>>

    private lateinit var detailViewModel: DetailViewModel
    private val dummyMovie = DummyCatalogue.getMovies()[0]
    private val dummyMovieId = dummyMovie.id
    private val dummyTvShow = DummyCatalogue.getTvShows()[0]
    private val dummyTvShowId = dummyTvShow.id

    @Before
    fun setUp() {
        detailViewModel = DetailViewModel(catalogueRepository)
    }

    @Test
    fun getDetailMovie() {
        val dummyDetailMovie = Resource.success(DummyCatalogue.getMovies()[0])
        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.value = dummyDetailMovie

        `when`(catalogueRepository.getDetailMovie(dummyMovieId.toString())).thenReturn(movie)
        val detailMovieEntity = dummyMovieId?.let { detailViewModel.getDetailMovie(it).value } as Resource<MovieEntity>
        verify(catalogueRepository).getDetailMovie(dummyMovieId.toString())

        assertNotNull(detailMovieEntity)
        detailMovieEntity.data?.let {
            assertEquals(dummyMovie.id, it.id)
            assertEquals(dummyMovie.title, it.title)
            assertEquals(dummyMovie.posterPath, it.posterPath)
            assertEquals(dummyMovie.overview, it.overview)
            assertEquals(dummyMovie.genres, it.genres)
            assertEquals(dummyMovie.voteAverage, it.voteAverage)
        }

        dummyMovieId.let { detailViewModel.getDetailMovie(it).observeForever(movieObserver) }
        verify(movieObserver).onChanged(dummyDetailMovie)
    }

    @Test
    fun getDetailTvShow() {
        val dummyDetailTvShow = Resource.success(DummyCatalogue.getTvShows()[0])
        val tvShow = MutableLiveData<Resource<TvShowEntity>>()
        tvShow.value = dummyDetailTvShow

        `when`(catalogueRepository.getDetailTvShow(dummyTvShowId.toString())).thenReturn(tvShow)
        val detailTvShowEntity = dummyTvShowId?.let { detailViewModel.getDetailTvShow(it).value } as Resource<TvShowEntity>
        verify(catalogueRepository).getDetailTvShow(dummyTvShowId.toString())

        assertNotNull(detailTvShowEntity)
        detailTvShowEntity.data?.let {
            assertEquals(dummyTvShow.id, it.id)
            assertEquals(dummyTvShow.name, it.name)
            assertEquals(dummyTvShow.posterPath, it.posterPath)
            assertEquals(dummyTvShow.overview, it.overview)
            assertEquals(dummyTvShow.genres, it.genres)
            assertEquals(dummyTvShow.voteAverage, it.voteAverage)
        }

        dummyTvShowId.let { detailViewModel.getDetailTvShow(it).observeForever(tvShowObserver) }
        verify(tvShowObserver).onChanged(dummyDetailTvShow)
    }

    @Test
    fun setFavoriteMovie() {
        val dummyDetailMovie = Resource.success(DummyCatalogue.getMovies()[0])
        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.value = dummyDetailMovie

        dummyDetailMovie.data?.let { detailViewModel.setFavoriteMovie(it) }
        verify(catalogueRepository).setFavoriteMovie(movie.value!!.data as MovieEntity, true)
        verifyNoMoreInteractions(movieObserver)
    }

    @Test
    fun setFavoriteTvShow() {
        val dummyDetailTvShow = Resource.success(DummyCatalogue.getTvShows()[0])
        val tvShow = MutableLiveData<Resource<TvShowEntity>>()
        tvShow.value = dummyDetailTvShow

        dummyDetailTvShow.data?.let { detailViewModel.setFavoriteTvShow(it) }
        verify(catalogueRepository).setFavoriteTvShow(tvShow.value!!.data as TvShowEntity, true)
        verifyNoMoreInteractions(movieObserver)
    }
}