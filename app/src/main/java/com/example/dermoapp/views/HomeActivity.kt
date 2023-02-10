package com.example.dermoapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.dermoapp.R
import com.example.dermoapp.databinding.ActivityHomeBinding
import com.example.dermoapp.fragments.AccountFragment
import com.example.dermoapp.fragments.ConfigurationFragment
import com.example.dermoapp.fragments.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeMenu: BottomNavigationView
    private lateinit var selectedFragment: Fragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        homeMenu = binding.homeMenu
        homeMenu.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home_menu_item -> {
                    goToFragment(HomeFragment())
                    true
                }
                R.id.account_menu_item -> {
                    goToFragment(AccountFragment())
                    true
                }
                R.id.config_menu_item -> {
                    goToFragment(ConfigurationFragment())
                    true
                }
                else -> false
            }
        }
        goToFragment(HomeFragment())
    }

    fun goToFragment(fragment: Fragment) {
        selectedFragment = fragment
        supportFragmentManager.beginTransaction().replace(
            R.id.home_frame_layout, selectedFragment
        ).commit()
    }
}