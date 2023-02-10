package com.example.dermoapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.dermoapp.R
import com.example.dermoapp.databinding.ActivityHomeBinding
import com.example.dermoapp.fragments.AccountFragment
import com.example.dermoapp.fragments.ConfigurationFragment
import com.example.dermoapp.fragments.HomeFragment
import com.example.dermoapp.viewmodels.HomeViewModel
import com.example.dermoapp.viewmodels.HomeViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.CircularProgressIndicator

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeMenu: BottomNavigationView
    private lateinit var selectedFragment: Fragment
    val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(application)
    }
    private lateinit var progressIndicator: CircularProgressIndicator
    private lateinit var frameLayout: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeMenu = binding.homeMenu
        homeMenu.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home_menu_item -> {
                    goToFragment(HomeFragment.newInstance(), R.string.home_fragment_title)
                    true
                }
                R.id.account_menu_item -> {
                    goToFragment(AccountFragment.newInstance(), R.string.account_fragment_title)
                    true
                }
                R.id.config_menu_item -> {
                    goToFragment(ConfigurationFragment.newInstance(), R.string.configuration_fragment_title)
                    true
                }
                else -> false
            }
        }
        val homeMenuItem = homeMenu.findViewById<View>(R.id.home_menu_item)
        homeMenuItem.performClick()

        progressIndicator = binding.progressIndicator
        frameLayout = binding.homeFrameLayout

        viewModel.loading.observe(this) { loading ->
            if(loading) {
                progressIndicator.visibility = View.VISIBLE
                homeMenu.visibility = View.GONE
                frameLayout.visibility = View.GONE
            } else {
                progressIndicator.visibility = View.GONE
                homeMenu.visibility = View.VISIBLE
                frameLayout.visibility = View.VISIBLE
            }
        }

        viewModel.errorMssg.observe(this) { mssg ->
            if(mssg != null) {
                openDialog(mssg)
            }
        }
    }

    fun goToFragment(fragment: Fragment, titleResourceId: Int) {
        supportActionBar?.title = resources.getString(titleResourceId)
        selectedFragment = fragment
        supportFragmentManager.beginTransaction().replace(
            R.id.home_frame_layout, selectedFragment
        ).commit()
    }

    fun recreateFromLocaleChanged() {
        val homeMenu = homeMenu.findViewById<View>(R.id.home_menu_item)
        homeMenu.performClick()
        recreate()
    }

    private fun openDialog(mssg: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.error)
            .setMessage(mssg)
            .setNeutralButton(R.string.ok) { _, _ ->
                viewModel.clearError()
            }.show()
    }
}