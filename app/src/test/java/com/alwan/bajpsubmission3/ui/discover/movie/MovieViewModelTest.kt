package com.alwan.bajpsubmission3.ui.discover.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.alwan.bajpsubmission3.data.CatalogueRepository
import com.alwan.bajpsubmission3.data.source.local.entity.MovieEntity
import com.alwan.bajpsubmission3.utils.SortUtils.VOTE_BEST
import com.alwan.bajpsubmission3.vo.Resource
import com.nhaarman.mockitokotlin2.verify
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
class MovieViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogueRepository: CatalogueRepository

    @Mock
    private lateinit var moviesObserver: Observer<Resource<PagedList<MovieEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    private lateinit var viewModel: MovieViewModel

    @Before
    fun setUp() {
        viewModel = MovieViewModel(catalogueRepository)
    }

    @Test
    fun getMovies() {
        val dummyMovies = Resource.success(pagedList)
        `when`(dummyMovies.data?.size).thenReturn(3)
        val movies = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        movies.value = dummyMovies

        `when`(catalogueRepository.getMovies(VOTE_BEST)).thenReturn(movies)
        val movie = viewModel.getMovies(VOTE_BEST).value?.data
        verify(catalogueRepository).getMovies(VOTE_BEST)
        assertNotNull(movie)
        assertEquals(3, movie?.size)

        viewModel.getMovies(VOTE_BEST).observeForever(moviesObserver)
        verify(moviesObserver).onChanged(dummyMovies)
    }
}