package com.alwan.bajpsubmission3.ui.favorite.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.alwan.bajpsubmission3.data.CatalogueRepository
import com.alwan.bajpsubmission3.data.source.local.entity.TvShowEntity
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
class FavoriteTvShowViewModelTest{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogueRepository: CatalogueRepository

    @Mock
    private lateinit var tvShowsObserver: Observer<PagedList<TvShowEntity>>

    @Mock
    private lateinit var pagedList: PagedList<TvShowEntity>

    private lateinit var viewModel: FavoriteTvShowViewModel

    @Before
    fun setUp() {
        viewModel = FavoriteTvShowViewModel(catalogueRepository)
    }

    @Test
    fun getFavoriteTvShows(){
        val dummyTvShows = pagedList
        `when`(dummyTvShows.size).thenReturn(3)
        val tvShows = MutableLiveData<PagedList<TvShowEntity>>()
        tvShows.value = dummyTvShows

        `when`(catalogueRepository.getFavoriteTvShows()).thenReturn(tvShows)
        val tvShow = viewModel.getFavoriteTvShows().value
        verify(catalogueRepository).getFavoriteTvShows()
        assertNotNull(tvShow)
        assertEquals(3, tvShow?.size)

        viewModel.getFavoriteTvShows().observeForever(tvShowsObserver)
        verify(tvShowsObserver).onChanged(dummyTvShows)
    }

    @Test
    fun setFavoriteTvShow(){
        val tvShow = DummyCatalogue.getTvShows()[0]

        viewModel.setFavoriteTvShow(tvShow)
        verify(catalogueRepository).setFavoriteTvShow(tvShow, true)
        verifyNoMoreInteractions(catalogueRepository)
    }
}