package com.example.dermoapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.dermoapp.R
import com.example.dermoapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        homeTextView = binding.homeTextView
        var name = ""

        val email = intent.getStringExtra("user_email")?.split("@")
        if (!email.isNullOrEmpty()){
            name = ", " + email.first()
        }

        homeTextView.text = getString(R.string.hello_field) + name
    }
}