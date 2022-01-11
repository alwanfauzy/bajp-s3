package com.alwan.bajpsubmission3.ui.favorite.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.alwan.bajpsubmission3.data.CatalogueRepository
import com.alwan.bajpsubmission3.data.source.local.entity.MovieEntity
import com.alwan.bajpsubmission3.utils.DummyCatalogue
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteMovieViewModelTest{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogueRepository: CatalogueRepository

    @Mock
    private lateinit var moviesObserver: Observer<PagedList<MovieEntity>>

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    private lateinit var viewModel: FavoriteMovieViewModel

    @Before
    fun setUp() {
        viewModel = FavoriteMovieViewModel(catalogueRepository)
    }

    @Test
    fun getFavoriteMovies(){
        val dummyMovies = pagedList
        `when`(dummyMovies.size).thenReturn(3)
        val movies = MutableLiveData<PagedList<MovieEntity>>()
        movies.value = dummyMovies

        `when`(catalogueRepository.getFavoriteMovies()).thenReturn(movies)
        val movie = viewModel.getFavoriteMovies().value
        verify(catalogueRepository).getFavoriteMovies()
        assertNotNull(movie)
        assertEquals(3, movie?.size)

        viewModel.getFavoriteMovies().observeForever(moviesObserver)
        verify(moviesObserver).onChanged(dummyMovies)
    }

    @Test
    fun setFavoriteMovie(){
        val movie = DummyCatalogue.getMovies()[0]

        viewModel.setFavoriteMovie(movie)
        verify(catalogueRepository).setFavoriteMovie(movie, true)
        verifyNoMoreInteractions(catalogueRepository)
    }
}