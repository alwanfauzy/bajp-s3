package com.alwan.bajpsubmission3.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alwan.bajpsubmission3.ui.discover.movie.MovieFragment
import com.alwan.bajpsubmission3.ui.discover.tvshow.TvShowFragment

class DiscoverPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = MovieFragment()
            1 -> fragment = TvShowFragment()
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}