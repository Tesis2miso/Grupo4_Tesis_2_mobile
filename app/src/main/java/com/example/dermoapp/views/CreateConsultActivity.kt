package com.example.dermoapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dermoapp.R
import com.example.dermoapp.databinding.ActivityConsultDetailBinding
import com.example.dermoapp.databinding.ActivityCreateConsultBinding

class CreateConsultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateConsultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateConsultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed();
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}