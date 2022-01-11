package com.alwan.bajpsubmission3.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.alwan.bajpsubmission3.R
import com.alwan.bajpsubmission3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.elevation = 0f

        setupNavigation()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_main) as NavHostFragment
        NavigationUI.setupWithNavController(
            binding.bottomNavMain,
            navHostFragment.navController
        )
    }
}
