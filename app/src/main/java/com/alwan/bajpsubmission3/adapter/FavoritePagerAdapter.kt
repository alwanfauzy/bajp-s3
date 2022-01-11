package com.alwan.bajpsubmission3.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alwan.bajpsubmission3.ui.favorite.movie.FavoriteMovieFragment
import com.alwan.bajpsubmission3.ui.favorite.tvshow.FavoriteTvShowFragment

class FavoritePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FavoriteMovieFragment()
            1 -> fragment = FavoriteTvShowFragment()
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}