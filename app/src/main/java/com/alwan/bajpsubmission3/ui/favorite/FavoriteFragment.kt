package com.alwan.bajpsubmission3.ui.favorite

import android.os.Bundle
import android.view.*
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.alwan.bajpsubmission3.R
import com.alwan.bajpsubmission3.adapter.FavoritePagerAdapter
import com.alwan.bajpsubmission3.databinding.FragmentFavoriteBinding
import com.google.android.material.tabs.TabLayoutMediator

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        val pagerAdapter = FavoritePagerAdapter(this)
        val viewPager = binding.viewPagerFavorite
        viewPager.adapter = pagerAdapter
        val tabs = binding.tabsFavorite
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}