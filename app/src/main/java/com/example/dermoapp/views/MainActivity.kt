package com.example.dermoapp.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dermoapp.databinding.ActivityMainBinding
import com.example.dermoapp.utils.SharedPreferencesManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        goToHomeIfNecessary()
    }

    fun goToHomeIfNecessary() {
        val token: String? = SharedPreferencesManager(this).getStringPreference(
            SharedPreferencesManager.USER_TOKEN
        )

        val email: String? = SharedPreferencesManager(this).getStringPreference(
            SharedPreferencesManager.USER_EMAIL
        )

        if(token != null && email != null) {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("user_email", email)
            startActivity(intent)
            this.finish()
        }
    }
}