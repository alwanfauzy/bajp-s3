package com.alwan.bajpsubmission3.ui.discover.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.alwan.bajpsubmission3.data.CatalogueRepository
import com.alwan.bajpsubmission3.data.source.local.entity.TvShowEntity
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
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogueRepository: CatalogueRepository

    @Mock
    private lateinit var tvShowObserver: Observer<Resource<PagedList<TvShowEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<TvShowEntity>

    private lateinit var tvShowViewModel: TvShowViewModel

    @Before
    fun setUp() {
        tvShowViewModel = TvShowViewModel(catalogueRepository)
    }

    @Test
    fun getTvShows() {
        val dummyTvShows = Resource.success(pagedList)
        Mockito.`when`(dummyTvShows.data?.size).thenReturn(3)
        val tvShows = MutableLiveData<Resource<PagedList<TvShowEntity>>>()
        tvShows.value = dummyTvShows

        Mockito.`when`(catalogueRepository.getTvShows(VOTE_BEST)).thenReturn(tvShows)
        val movie = tvShowViewModel.getTvShows(VOTE_BEST).value?.data
        verify(catalogueRepository).getTvShows(VOTE_BEST)
        assertNotNull(movie)
        assertEquals(3, movie?.size)

        tvShowViewModel.getTvShows(VOTE_BEST).observeForever(tvShowObserver)
        verify(tvShowObserver).onChanged(dummyTvShows)
    }
}