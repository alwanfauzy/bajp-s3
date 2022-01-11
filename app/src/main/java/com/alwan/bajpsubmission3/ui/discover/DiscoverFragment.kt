package com.alwan.bajpsubmission3.ui.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.alwan.bajpsubmission3.R
import com.alwan.bajpsubmission3.adapter.DiscoverPagerAdapter
import com.alwan.bajpsubmission3.databinding.FragmentDiscoverBinding
import com.google.android.material.tabs.TabLayoutMediator

class DiscoverFragment : Fragment() {
    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscoverBinding.inflate(layoutInflater, container, false)

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
        val pagerAdapter = DiscoverPagerAdapter(this)
        val viewPager = binding.viewPagerDiscover
        viewPager.adapter = pagerAdapter
        val tabs = binding.tabsDiscover
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