package com.alwan.bajpsubmission3.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.alwan.bajpsubmission3.R
import com.alwan.bajpsubmission3.data.source.local.entity.MovieEntity
import com.alwan.bajpsubmission3.data.source.local.entity.TvShowEntity
import com.alwan.bajpsubmission3.utils.DummyCatalogue
import com.alwan.bajpsubmission3.utils.EspressoIdlingResource
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class MainActivityTest {

    private val dummyDetailMovie = DummyCatalogue.getMovies()[0]
    private val dummyDetailTvShow = DummyCatalogue.getTvShows()[0]
    private val emptyDataMovie = emptyList<MovieEntity>()
    private val emptyDataTvShow = emptyList<TvShowEntity>()

    @Before
    fun setup() {
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @Test
    fun clickChangeLanguage() {
        onView(withId(R.id.menu_language)).perform(click())
    }

    @Test
    fun loadDataMovie() {
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                19
            )
        )
    }

    @Test
    fun loadDataTvShow() {
        onView(withText(R.string.tab_text_2)).perform(click())
        onView(withId(R.id.rv_tv_show)).apply {
            check(matches(isDisplayed()))
            perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(19))
        }
    }

    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        checkDetailMovie(dummyDetailMovie)
    }

    @Test
    fun loadDetailTvShow() {
        onView(withText(R.string.tab_text_2)).perform(click())
        onView(withId(R.id.rv_tv_show)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        checkDetailTvShow(dummyDetailTvShow)
    }

    @Test
    fun emptyDataMovie() {
        assertEquals(emptyDataMovie.size, 0)
        onView(withId(R.id.img_empty_movie)).perform(setVisibility(true))
        onView(withId(R.id.tv_empty_movie)).perform(setVisibility(true))
        onView(withId(R.id.rv_movie)).perform(setVisibility(false))
    }

    @Test
    fun emptyDataTvShow() {
        onView(withText(R.string.tab_text_2)).perform(click())
        assertEquals(emptyDataTvShow.size, 0)
        onView(withId(R.id.img_empty_tv_show)).perform(setVisibility(true))
        onView(withId(R.id.tv_empty_tv_show)).perform(setVisibility(true))
        onView(withId(R.id.rv_tv_show)).perform(setVisibility(false))
    }

    @Test
    fun loadDataFavoriteMovie() {
        onView(withId(R.id.menu_favorite)).perform(click())
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                19
            )
        )
    }

    @Test
    fun loadDataFavoriteTvShow() {
        onView(withId(R.id.menu_favorite)).perform(click())
        onView(withText(R.string.tab_text_2)).perform(click())
        onView(withId(R.id.rv_tv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_show)).apply {
            check(matches(isDisplayed()))
            perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(19))
        }
    }

    @Test
    fun loadDetailFavoriteMovie() {
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.menu_add_favorite)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.menu_favorite)).perform(click())
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        checkDetailMovie(dummyDetailMovie)
    }

    @Test
    fun loadDetailFavoriteTvShow() {
        onView(withText(R.string.tab_text_2)).perform(click())
        onView(withId(R.id.rv_tv_show)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.menu_add_favorite)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.menu_favorite)).perform(click())
        onView(withText(R.string.tab_text_2)).perform(click())
        onView(withId(R.id.rv_tv_show)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        checkDetailTvShow(dummyDetailTvShow)
    }

    @Test
    fun setFavoriteMovie() {
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.menu_add_favorite)).perform(click())
        onView(withId(R.id.menu_add_favorite)).perform(click())
    }

    @Test
    fun setFavoriteTvShow() {
        onView(withText(R.string.tab_text_2)).perform(click())
        onView(withId(R.id.rv_tv_show)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.menu_add_favorite)).perform(click())
        onView(withId(R.id.menu_add_favorite)).perform(click())
    }

    private fun checkDetailMovie(movieEntity: MovieEntity) {
        onView(withId(R.id.tv_title_detail)).apply {
            check(matches(isDisplayed()))
            check(matches(withText(movieEntity.title)))
        }
        onView(withId(R.id.tv_score_detail)).apply {
            check(matches(isDisplayed()))
            check(matches(withText(movieEntity.voteAverage.toString())))
        }
        onView(withId(R.id.tv_genre_detail)).apply {
            check(matches(isDisplayed()))
            check(matches(withText(movieEntity.genres)))
        }
        onView(withId(R.id.tv_overview_detail)).apply {
            check(matches(isDisplayed()))
            check(matches(withText(movieEntity.overview)))
        }
        onView(withId(R.id.img_poster_detail)).apply {
            check(matches(isDisplayed()))
            check(matches(withTagValue(equalTo(movieEntity.posterPath))))
        }
    }

    private fun checkDetailTvShow(tvShowEntity: TvShowEntity) {
        onView(withId(R.id.tv_title_detail)).apply {
            check(matches(isDisplayed()))
            check(matches(withText(tvShowEntity.name)))
        }
        onView(withId(R.id.tv_score_detail)).apply {
            check(matches(isDisplayed()))
            check(matches(withText(tvShowEntity.voteAverage.toString())))
        }
        onView(withId(R.id.tv_genre_detail)).apply {
            check(matches(isDisplayed()))
            check(matches(withText(tvShowEntity.genres)))
        }
        onView(withId(R.id.tv_overview_detail)).apply {
            check(matches(isDisplayed()))
            check(matches(withText(tvShowEntity.overview)))
        }
        onView(withId(R.id.img_poster_detail)).apply {
            check(matches(isDisplayed()))
            check(matches(withTagValue(equalTo(tvShowEntity.posterPath))))
        }
    }

    private fun setVisibility(value: Boolean): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(View::class.java)
            }

            override fun perform(uiController: UiController?, view: View) {
                view.visibility = if (value) View.VISIBLE else View.GONE
            }

            override fun getDescription(): String {
                return "Show / Hide View"
            }
        }
    }
}